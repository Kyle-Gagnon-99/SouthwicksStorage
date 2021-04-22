package com.southwicksstorage.southwicksstorage.models.formModels;

public class ReadNotificationForm {

	private int id;
	
	public ReadNotificationForm() {
		
	}
	
	public ReadNotificationForm(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
}
