package com.southwicksstorage.southwicksstorage.models;

public class NotificationModel {
	
	public NotificationModel(int id, String notificationType, String notificationMessage, boolean isRead) {
		super();
		this.id = id;
		this.notificationType = notificationType;
		this.notificationMessage = notificationMessage;
		this.setRead(isRead);
	}
	private int id;
	private String notificationType;
	private String notificationMessage;
	private boolean isRead;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getNotificationMessage() {
		return notificationMessage;
	}
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	/**
	 * @return the isRead
	 */
	public boolean isRead() {
		return isRead;
	}
	/**
	 * @param isRead the isRead to set
	 */
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}
