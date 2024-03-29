package com.southwicksstorage.southwicksstorage.models;

import com.southwicksstorage.southwicksstorage.constants.Roles;

public class UserModel {
	
	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String role;
	private Roles roleEnum;
	private String phoneNumber;
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	public Roles getRoleEnum() {
		return roleEnum;
	}
	
	public void setRoleEnum(Roles roleEnum) {
		this.roleEnum = roleEnum;
	}
	
	public UserModel(int id, String username, String firstName, String lastName, String role, Roles roleEnum, String phoneNumber) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.roleEnum = roleEnum;
		this.phoneNumber = phoneNumber;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
