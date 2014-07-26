package com.pradeya.cast.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.annotation.Id;

public class Media implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String  MEDIA_VIDEO = "VIDEO";
	public static final String  MEDIA_IMAGE = "IMAGE";
	public static final String  MEDIA_TEXT = "TEXT";
	public static final String  MEDIA_AUDIO = "AUDIO";

	@Id
	private long id;
	private long orgId;

	private String mediaType;
	private String mediaPath;
	private String userName;
	private Date creationTime;
	private Date dateApproved;
	private String approvedBy;
	private String state;
	private boolean approved;
	private String mediaFileName;
	private String mediaFileNameWithoutExt;
	private boolean video;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public String getMediaPath() {
		return mediaPath;
	}

	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreationTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getMediaFileName() {
		return mediaFileName;
	}

	public void setMediaFileName(String mediaFileName) {
		this.mediaFileName = mediaFileName;
	}

	public String getMediaFileNameWithoutExt() {
		return mediaFileNameWithoutExt;
	}

	public void setMediaFileNameWithoutExt(String mediaFileNameWithoutExt) {
		this.mediaFileNameWithoutExt = mediaFileNameWithoutExt;
	}

	public boolean getVideo() {
		return mediaType.equals(MEDIA_VIDEO);
	}

	public void setVideo(boolean video) {
		this.video = video;
	}
	
	
}