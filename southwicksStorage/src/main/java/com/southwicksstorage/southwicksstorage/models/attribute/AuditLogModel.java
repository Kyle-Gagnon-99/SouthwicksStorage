/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.attribute;

/**
 * @author kyle
 *
 */
public class AuditLogModel {
	
	/**
	 * This is the who changed the entity
	 */
	private String username;
	/**
	 * When it was changed
	 */
	private String timeDate;
	/**
	 * The name of the entity
	 */
	private String itemName;
	/**
	 * The entity's location
	 */
	private String itemLocation;
	/**
	 * The old value
	 */
	private Object oldValue;
	/**
	 * The new value
	 */
	private Object newValue;
	/**
	 * Whether or not it was an insert type
	 */
	private boolean isInsert;
	/**
	 * Whether or not it was a modify type
	 */
	private boolean isModify;
	/**
	 * Whether or not it was a delete type
	 */
	private boolean isDelete;
	
	/**
	 * @param username
	 * @param timeAndDate
	 * @param itemName
	 * @param itemLocation
	 * @param oldValue
	 * @param newValue
	 */
	public AuditLogModel(String username, String timeDate, String itemName, String itemLocation, Object oldValue,
			Object newValue, boolean isInsert, boolean isModify, boolean isDelete) {
		this.username = username;
		this.timeDate = timeDate;
		this.itemName = itemName;
		this.itemLocation = itemLocation;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.setInsert(isInsert);
		this.setModify(isModify);
		this.setDelete(isDelete);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(String timeDate) {
		this.timeDate = timeDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemLocation() {
		return itemLocation;
	}

	public void setItemLocation(String itemLocation) {
		this.itemLocation = itemLocation;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	public boolean isInsert() {
		return isInsert;
	}

	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}

	public boolean isModify() {
		return isModify;
	}

	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	@Override
	public String toString() {
		String returnString = "Uh-oh it looks like something went wrong :(";
		
		if(this.isInsert) {
			returnString = String.format("%s located at %s was added by %s", itemName, itemLocation, username);
		} else if(this.isModify) {
			returnString = String.format("%s located at %s was changed from %d to %d by %s", itemName, itemLocation, oldValue, newValue, username);
		} else if(this.isDelete) {
			returnString = String.format("%s located at %s was deleted by %s", itemName, itemLocation, username);
		}
		
		return returnString;
	}

}
