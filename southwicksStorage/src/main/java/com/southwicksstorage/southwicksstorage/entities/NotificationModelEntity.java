package com.southwicksstorage.southwicksstorage.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.southwicksstorage.southwicksstorage.constants.NotificationMessages;
import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;

@Entity()
@Table(name = "user_notification")
public class NotificationModelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "notificationMessage")
	private NotificationMessages message;
	
	@Column(name = "isRead")
	private boolean isRead;
	
	@Column(name = "notificationType")
	private NotificationTypes notificationType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName="id")
	private UserModelEntity userModel;
	
	public NotificationModelEntity() {
		/* Defualt Constructor */
	}
	
	public NotificationModelEntity(NotificationMessages message, NotificationTypes notificationType, boolean isRead, UserModelEntity userModel) {
		this.message = message;
		this.notificationType = notificationType;
		this.isRead = isRead;
		this.userModel = userModel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NotificationMessages getMessage() {
		return message;
	}

	public void setMessage(NotificationMessages message) {
		this.message = message;
	}

	public NotificationTypes getNotificationType() {
		return notificationType;
	}
	
	public void setNotificationType(NotificationTypes notificationType) {
		this.notificationType = notificationType;
	}
	
	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public UserModelEntity getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModelEntity userModel) {
		this.userModel = userModel;
	}
	
}
