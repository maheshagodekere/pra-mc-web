package com.pradeya.cast.ui.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pradeya.cast.domain.Media;

@Component
@Scope("session")
public class ImageDaoBean {

	private Logger logger = LoggerFactory.getLogger(ImageDaoBean.class);
	
	public StreamedContent getImage() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();

			if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
				logger.info("#### PhaseId.RENDER_RESPONSE ####");
				// So, we're rendering the view. Return a stub StreamedContent
				// so that it will generate right URL.
				return new DefaultStreamedContent();
			} else {
				// So, browser is requesting the image. Get ID value from actual
				// request param.
				
				FacesContext fc = FacesContext.getCurrentInstance();
			    Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
				
				String mediaPath = params.get("mpath");
				String mediaFileName = params.get("mediaFileName");
				String mediaType = params.get("mediaType");
				
				logger.debug("#### ACTUAL_RESPONSE.......mediaPath {} ", mediaPath);
				
				if(Media.MEDIA_VIDEO.equalsIgnoreCase(mediaType)){
					//mediaPath = mediaPath+File.separator+mediaFileName.replace(".mp4", ".png");
					mediaPath = mediaPath+File.separator+"thumbnail.jpg";
				} else {
					mediaPath = mediaPath+File.separator+mediaFileName;
				}
				
				File sourceimage = new File(mediaPath);
				InputStream is = new BufferedInputStream(new FileInputStream(sourceimage));

				return new DefaultStreamedContent(is, "image/png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPath() {

		FacesContext fc = FacesContext.getCurrentInstance();
	    Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	    String mediaPath = params.get("apath"); 		

		logger.debug("Context - {} ", fc.getExternalContext().getRequestMap());

		Media cmedia = (Media) fc.getExternalContext().getRequestMap().get("media");
		if (cmedia != null)
			mediaPath = cmedia.getMediaPath();

		logger.debug("Media from the context {} and media path is {}", cmedia, mediaPath);
		
		return mediaPath;
	}
}
