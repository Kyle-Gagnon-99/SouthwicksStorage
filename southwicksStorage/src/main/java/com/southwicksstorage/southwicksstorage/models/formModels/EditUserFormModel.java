package com.southwicksstorage.southwicksstorage.models.formModels;

public class EditUserFormModel {
	
	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String role;
	private String resetPassword;
	

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getResetPassword() {
		return resetPassword;
	}
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}
	
	public EditUserFormModel(String firstName, String lastName, String username, String role,
			String resetPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.role = role;
		this.resetPassword = resetPassword;
	}
	
	public EditUserFormModel() {
		
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
