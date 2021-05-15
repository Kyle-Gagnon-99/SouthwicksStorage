/**
 * 
 */
package com.southwicksstorage.southwicksstorage.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "notification_message")
public class NotificationMessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "Stand name can not be empty")
	@Column(name = "message", unique = true)
	private String message;
	
	public NotificationMessageEntity() {
		
	}

	public NotificationMessageEntity(String message) {
		this.message = message;
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
	
}
