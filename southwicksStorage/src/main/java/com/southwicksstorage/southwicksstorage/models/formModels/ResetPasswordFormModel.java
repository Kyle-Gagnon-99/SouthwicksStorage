package com.southwicksstorage.southwicksstorage.models.formModels;

public class ResetPasswordFormModel {

	private String currentPassword;
	private String newPassword;
	private String retypeNewPassword;
	
	public ResetPasswordFormModel() {
		/* Default Constructor */
	}
	
	/**
	 * Constructor for the post form for resetting password
	 * @param currentPassword The current password
	 * @param newPassword The new password
	 * @param retypeNewPassword The new password retyped for confirmation
	 */
	public ResetPasswordFormModel(String currentPassword, String newPassword, String retypeNewPassword) {
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.retypeNewPassword = retypeNewPassword;
	}
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRetypeNewPassword() {
		return retypeNewPassword;
	}
	public void setRetypeNewPassword(String retypeNewPassword) {
		this.retypeNewPassword = retypeNewPassword;
	}
	
}
