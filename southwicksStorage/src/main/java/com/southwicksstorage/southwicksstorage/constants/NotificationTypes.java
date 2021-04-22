package com.southwicksstorage.southwicksstorage.constants;

public enum NotificationTypes {
	INFO("INFO", 0),
	SUCCESS("SUCCESS", 1),
	WARNING("WARNING", 2),
	ERROR("ERROR", 3);
	
	private String type;
	private int priority;
	
	private NotificationTypes(String type, int priority) {
		this.type = type;
		this.priority = priority;
	}
	
	public String getType() {
		return type;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public static String findTypeByPriority(int priority) {
		NotificationTypes[] notifications = NotificationTypes.values();
		int notificationLength = notifications.length;
		String returnString = null;
		
		for(int index = 0; index < notificationLength; index++) {
			if(priority == notifications[index].getPriority()) {
				returnString = notifications[index].getType();
				break;
			}
		}
		
		return returnString;
	}
}
