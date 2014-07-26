package com.pradeya.cast.domain;


import java.util.Date;

public class Schedule {
	private final static String TAG = Schedule.class.getName();
	public final static int TOTAL_DISPLAY = 360; // # of 10 seconds slots / hour
	public final static String PENDING = "PENDING";// Push State
	public final static String NOT_AVAILABLE = "NA";// reason

	private long id;

	private Date displayDate ;
	private long displayId = 0L;
	private int hour = 0;

	private Date creationTime = new Date();
	
	private long mediaId;
	private String userName;
	private Date scheduleDate = new Date();
	
	private int slots;
	
	private String pushState = PENDING;
	private String pushTime;
	private String pushedBy;
	private String reason = NOT_AVAILABLE;

	private boolean approved = true;
	private String approvedBy;
	
	private String location;
	

	
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	public Schedule(){}
	public Schedule(Date displayDate, long displayId, int hour) {
		
		this.displayDate = displayDate;
		this.displayId = displayId;
		this.hour = hour;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("########## Schedule ##########\n");
		sb.append("displayDate: "+ displayDate.toString()+"\n");
		sb.append("displayId  : "+ displayId+"\n");
		sb.append("hour       : "+ hour+"\n");
		sb.append("mediaId    : "+ mediaId+"\n");
		sb.append("userName   : "+ userName+"\n");
		sb.append("slots      : "+ slots);
		// TODO Auto-generated method stub
		return sb.toString();
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the displayDate
	 */
	public Date getDisplayDate() {
		return displayDate;
	}

	/**
	 * @param displayDate
	 *            the displayDate to set
	 */
	public void setDisplayDate(Date displayDate) {
		
		this.displayDate = displayDate;
	}

	/**
	 * @return the displayId
	 */
	public long getDisplayId() {
		return displayId;
	}

	/**
	 * @param displayId
	 *            the displayId to set
	 */
	public void setDisplayId(long displayId) {
		this.displayId = displayId;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @return the mediaId
	 */
	public long getMediaId() {
		return mediaId;
	}

	/**
	 * @param mediaId
	 *            the mediaId to set
	 */
	public void setMediaId(long mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * @return the userId
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the scheduleDate
	 */
	public Date getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param scheduleDate
	 *            the scheduleDate to set
	 */
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * @return the slots
	 */
	public int getSlots() {
		return slots;
	}
	/**
	 * @param slots the slots to set
	 */
	public void setSlots(int slots) {
		this.slots = slots;
	}
	/**
	 * @return the pushState
	 */
	public String getPushState() {
		return pushState;
	}

	/**
	 * @param pushState
	 *            the pushState to set
	 */
	public void setPushState(String pushState) {
		this.pushState = pushState;
	}

	/**
	 * @return the pushTime
	 */
	public String getPushTime() {
		return pushTime;
	}

	/**
	 * @param pushTime
	 *            the pushTime to set
	 */
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}

	/**
	 * @return the pushedBy
	 */
	public String getPushedBy() {
		return pushedBy;
	}

	/**
	 * @param pushedBy
	 *            the pushedBy to set
	 */
	public void setPushedBy(String pushedBy) {
		this.pushedBy = pushedBy;
	}

	/**
	 * @return the approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * @param approved
	 *            the approved to set
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy
	 *            the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

}
