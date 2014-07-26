/* package com.pradeya.cast.ui.bean;



import java.io.InputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pradeya.cast.service.DocumentService;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

@SuppressWarnings("serial")
@Component
  
public class FileUploadBean implements Serializable {  
	
	@Autowired
	DocumentService documentService;
	
	UploadedFile file;
    public void handleFileUpload(FileUploadEvent event) {  
    	
    	try {
			UploadedFile uf = event.getFile();
			InputStream is = uf.getInputstream();
			

			GridFS gfs = new GridFS();
			
			GridFSInputFile gfsFile = gfs.createFile(is, uf.getFileName());
			//gfsFile.put("pid", news.getId());
			gfsFile.setContentType(uf.getContentType());
			gfsFile.save();
			
			System.out.println("success");

			FacesMessage msg = new FacesMessage("Succesful", uf.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	
 
    	
        
    }  
}  
 */

package com.pradeya.cast.ui.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.pradeya.cast.domain.Display;
import com.pradeya.cast.domain.Media;
import com.pradeya.cast.domain.Schedule;
import com.pradeya.cast.service.DisplayService;
import com.pradeya.cast.service.MediaService;
import com.pradeya.cast.service.ScheduleService;
import com.pradeya.cast.util.CastUtil;
import com.pradeya.cast.util.VideoThumbnailCreatorUtil;

@Component
@Scope("session")
public class FileUploadController {

	private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Value("${media.repository.base.location}")
	private String destination;

	@Autowired
	MediaService mediaService;

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	DisplayService displayService;

	@Autowired
	UserUIMedia userUIMedia;

	@PostConstruct
	public void init() {

	}

	public void upload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Success! ", event.getFile()
				.getFileName() + " is uploaded.");
		
		try {
			copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());
			
		} catch (Exception e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"File already present! ", event.getFile()
					.getFileName() );
			
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);

		
	}

	public void copyFile(String fileName, InputStream in) throws Exception{

		long orgId = ((Long) RequestContextHolder.currentRequestAttributes()
				.getAttribute(CastUtil.ID, RequestAttributes.SCOPE_SESSION))
				.longValue();
		String who = RequestContextHolder.currentRequestAttributes()
				.getAttribute(CastUtil.WHO, RequestAttributes.SCOPE_SESSION)
				.toString();

		OutputStream out = null;
		try {

			String absoluteFilePath = fileName;
			if (fileName.contains("\\")) {
				fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
			} else if (fileName.contains("/")) {
				fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
			}

			fileName = fileName.toLowerCase();
			// write the inputStream to a FileOutputStream
			String path = destination + File.separator + orgId;
			File f = new File(path);
			if (!f.exists())
				f.mkdir();
			path = path + File.separator + who;
			f = new File(path);
			if (!f.exists())
				f.mkdir();

			logger.info("Upload file path - {}", path);
			logger.debug("Upload file name - {} ", fileName);

			File fullPath = new File(path + File.separator + fileName);

			if (!fullPath.exists()) {

				out = new FileOutputStream(new File(path + File.separator
						+ fileName));

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				logger.debug("New media file {} Uploaded! ", absoluteFilePath);
				in.close();
				out.flush();
				out.close();

				if (fileName.contains(".mp4")) {
					// Create a Thumbnail image if uploaded media is of type
					// video
					// TODO: Run a separate thread do this task as it is a
					// blocking
					// activity
					
					//Somehow thumbnail generation is crasging....hence commented
					//ThumbnailCreator thumbnailCreator = new ThumbnailCreator(
					//		path, fileName);
					//thumbnailCreator.start();
					// VideoThumbnailCreatorUtil.createThumbnail(path+File.separator+"user8"+File.separator+fileName);
					createMediaRepository(path, fileName, Media.MEDIA_VIDEO);
				} else {
					createMediaRepository(path, fileName, Media.MEDIA_IMAGE);
				}

				userUIMedia.findUserMedia();
				logger.debug(
						"Repository entry successful for the uploaded file - {} ",
						absoluteFilePath);
			}else{
				throw new Exception("File already present");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
			
		}finally{
			if (in != null)
				try {
					in.close();
					if (out != null)
						out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}

	public void createMediaRepository(String path, String fileName,
			String mediaType) {

		long orgId = ((Long) RequestContextHolder.currentRequestAttributes()
				.getAttribute(CastUtil.ID, RequestAttributes.SCOPE_SESSION))
				.longValue();
		String who = RequestContextHolder.currentRequestAttributes()
				.getAttribute(CastUtil.WHO, RequestAttributes.SCOPE_SESSION)
				.toString();

		Media media = new Media();

		// media.setId(mediaId);
		media.setOrgId(orgId);
		media.setMediaType(mediaType);
		media.setMediaPath(path + File.separator);
		media.setUserName(who);
		media.setMediaFileName(fileName);
		media.setMediaFileNameWithoutExt(fileName.substring(0,
				fileName.lastIndexOf(".")));

		Date cdate = new Date();
		cdate.setTime(System.currentTimeMillis());
		media.setCreationTime(cdate);
		media.setApproved(true);
		// media.setApprovedBy("user1");
		media.setState("ACTIVE");
		Media newMedia = mediaService.createMedia(media);

		ArrayList<Display> displays = displayService.findAllDisplay();

		if (null != newMedia && displays != null && displays.size()>0) {
			for (Display display : displays){
				Schedule newSchedule = getSchedule(newMedia.getId(),
						newMedia.getUserName());
				newSchedule.setDisplayId(display.getId());
				scheduleService.createSchedule(newSchedule);
			}
		
		
		}
	}

	private Schedule getSchedule(long mediaId, String userName) {

		Schedule schedule = new Schedule();

		schedule.setDisplayDate(new Date());
		//schedule.setDisplayId("Jayanagar: Display1");
		schedule.setHour(8);
		schedule.setMediaId(mediaId);
		schedule.setUserName(userName);
		schedule.setSlots(10);
		System.out.println(schedule.toString());

		return schedule;
	}

//	private class ThumbnailCreator extends Thread {
//		String path = null;
//		String fileName = null;
//
//		ThumbnailCreator(String path, String fileName) {
//			this.path = path;
//			this.fileName = fileName;
//		}
//
//		public void run() {
//			logger.info("Thumbnail image creation started!!!");
//			VideoThumbnailCreatorUtil.createThumbnail(path + File.separator
//					+ fileName);
//			logger.info("Thumbnail image created!!!");
//		}
//	}
}