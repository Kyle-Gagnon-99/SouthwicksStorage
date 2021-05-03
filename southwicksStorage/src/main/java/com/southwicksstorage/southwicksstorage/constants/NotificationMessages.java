package com.southwicksstorage.southwicksstorage.constants;

public enum NotificationMessages {

	/* If adding new notification messages add them to the bottom of the list to prevent
	 * any messed up data in the db
	 */
	DEFAULT_PASSWORD_MESSAGE("Your password is the default password. Please change it"),
	ZEBRA_OUT_OF_STOCK_MESSAGE("Zebra is out of some items! You may need to stock these items as soon as possible");
	
	private String message;
	
	private NotificationMessages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
