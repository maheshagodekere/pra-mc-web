package com.pradeya.cast.ui.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.pradeya.cast.domain.Media;
import com.pradeya.cast.service.MediaService;
import com.pradeya.cast.util.CastUtil;

@Component
@Scope("session")
public class UserUIMedia implements Serializable {

	private List<Media> mediaList = new ArrayList<Media>();

	private Logger logger = LoggerFactory.getLogger(UserUIMedia.class);

	@Autowired
	MediaService mediaService;

	@PostConstruct
	public void findUserMedia() {
		//String who =  RequestContextHolder.currentRequestAttributes().getAttribute(CastUtil.WHO, RequestAttributes.SCOPE_SESSION).toString();

		//Iterable<Media> itm = mediaService.findMediaByUserName(who);
		Iterable<Media> itm = mediaService.findMediaByUserName("mahesh");
		Iterator<Media> it = itm.iterator();
		mediaList.clear();
		while (it.hasNext()) {
			mediaList.add(it.next());
		}
	}

	
	public int getMediaCount() {
		return mediaList.size();
	}

	public List<Media> getAllMedia() {
		logger.debug("###############################################....returning madia:"+mediaList);
		logger.debug("###############################################....returning madia:"+mediaList.size());
		
		return mediaList;
	}

	private Media mediaItem = null;

	public Media getMediaItem() {
		return mediaItem;
	}

	public void setMediaItem(Media mediaItem) {
		this.mediaItem = mediaItem;
	}
	
//	public void mediaDetails(){
//
//		FacesContext fc = FacesContext.getCurrentInstance();
//	    Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
//	    String mediaId = params.get("mediaId");
//
//		if(mediaId == null){
//			navBean.setActivePage("mediaListView");
//		}
//
//		Media mediaItem = mediaService.findMedia(mediaId);
//
//		if(mediaItem != null)
//			setMediaItem(mediaItem);
//
//		logger.debug("mediaItem: Path - {} ", mediaItem.getMediaPath());
//
//		navBean.setActivePage("mediaDetailsView");
//	}
}
