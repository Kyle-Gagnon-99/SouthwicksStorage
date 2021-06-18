package com.southwicksstorage.southwicksstorage.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;

@Entity()
@Table(name = "user_notification")
public class NotificationModelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_message_id", referencedColumnName="id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private NotificationMessageEntity message;
	
	@Column(name = "is_read")
	private boolean isRead;
	
	@Column(name = "is_visible")
	private boolean isVisible;
	
	@Column(name = "date_created")
	private LocalDateTime dateCreated;
	
	@Column(name = "notificationType")
	private NotificationTypes notificationType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName="id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModelEntity userModel;
	
	public NotificationModelEntity() {
		/* Defualt Constructor */
	}
	
	public NotificationModelEntity(NotificationMessageEntity message, NotificationTypes notificationType, boolean isRead, boolean isVisible, 
			LocalDateTime dateCreated, UserModelEntity userModel) {
		this.message = message;
		this.notificationType = notificationType;
		this.isRead = isRead;
		this.isVisible = isVisible;
		this.dateCreated = dateCreated;
		this.userModel = userModel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NotificationMessageEntity getMessage() {
		return message;
	}

	public void setMessage(NotificationMessageEntity message) {
		this.message = message;
	}

	public NotificationTypes getNotificationType() {
		return notificationType;
	}
	
	public void setNotificationType(NotificationTypes notificationType) {
		this.notificationType = notificationType;
	}
	
	public boolean getIsRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	public boolean getIsVisible() {
		return isVisible;
	}
	
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public UserModelEntity getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModelEntity userModel) {
		this.userModel = userModel;
	}
	
}
