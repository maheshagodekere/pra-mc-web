package com.pradeya.cast.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.pradeya.cast.domain.Media;
import com.pradeya.cast.domain.Schedule;


@SuppressWarnings("serial")
public class MediaDownloadServlet extends HttpServlet {

	private Logger logger = LoggerFactory.getLogger(MediaDownloadServlet.class);
	public static final String RESULT = "result";

	@Autowired
	MediaService mediaService;

	@Autowired
	ScheduleService scheduleService;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...: Media Download Servlet INIT");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mid = request.getParameter(GoogleNotificationService.MEDIA_ID);
		String sid = request
				.getParameter(GoogleNotificationService.SCHEDULE_ID);

		String downloadresult = request.getParameter(RESULT);
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...: mid: "+mid);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...: sid: "+sid);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...: downloadresult: "+downloadresult);


		
		if ((downloadresult != null && 
				downloadresult.trim() != "") &&
				(sid != null &&
				sid.trim() != "")) {
			//scheduleService.update(schedule)pdatePushState(sid,"DOWNLOADED");
			response.setStatus(200);
			return;
		}

		if (mid == null) {
			logger.error("mediaId should not be null to retrieve media");
			return;
		}

		logger.info("mediaId from request is {} ", mid);

		// Get the Media object for the mediaId
		Media media = mediaService.findMedia(Long.parseLong(mid));

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...: Got Media: "+media);
		
		// Retrieve the media path
		String mediaBasePath = media.getMediaPath();
		String mediaFileName = media.getMediaFileName();

		logger.info("File base path is {}", mediaBasePath);

		FileInputStream fis = null;
		ServletOutputStream out = null;
		// Stream media content
		try {

			String mediaPath = mediaBasePath + File.separator + mediaFileName;
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...: Media Path to download: "+mediaPath);

			File file = new File(mediaPath);
			fis = new FileInputStream(file);

			response.setContentLength((int) file.length());
			String fileName = file.getName();

			if (mediaPath.contains(".mp4")) {
				response.setContentType("video/mp4");
			}
			response.setHeader("Content-Disposition",
					"attachment; filename=" + fileName );
			
			out = response.getOutputStream();
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = fis.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			logger.info("streaming media content is over!!!");
			System.out.println("%%%%...: Media Download done");
			if(sid!=null){
				Schedule schedule = scheduleService.findSchedule(Long.parseLong(sid));
				if(schedule!=null){
					schedule.setPushState("DOWNLOADED");
					scheduleService.update(schedule);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();

			if (fis != null)
				fis.close();
		}
	}
}
