/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.system;

/**
 * @author kyle
 *
 */
public class NotificationMessage {
	
	private String message;
	
	public NotificationMessage(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
