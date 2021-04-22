package com.southwicksstorage.southwicksstorage.constants;

public enum NotificationMessages {

	/* If adding new notification messages add them to the bottom of the list to prevent
	 * any messed up data in the db
	 */
	DEFAULT_PASSWORD_MESSAGE("Your password is the default password. Please change it");
	
	private String message;
	
	private NotificationMessages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
