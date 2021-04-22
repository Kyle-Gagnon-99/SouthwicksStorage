package com.southwicksstorage.southwicksstorage.models;

public class NotificationModel {
	
	public NotificationModel(int id, String notificationType, String notificationMessage) {
		super();
		this.id = id;
		this.notificationType = notificationType;
		this.notificationMessage = notificationMessage;
	}
	private int id;
	private String notificationType;
	private String notificationMessage;
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

}
