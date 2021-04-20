package com.southwicksstorage.southwicksstorage.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "user_notification")
public class NotificationModelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "notificationMessage", length = 2500)
	private String message;
	
	@Column(name = "isRead")
	private boolean isRead;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName="id")
	private UserModelEntity userModel;
	
	public NotificationModelEntity() {
		/* Defualt Constructor */
	}
	
	public NotificationModelEntity(String message, boolean isRead, UserModelEntity userModel) {
		this.message = message;
		this.isRead = isRead;
		this.userModel = userModel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
