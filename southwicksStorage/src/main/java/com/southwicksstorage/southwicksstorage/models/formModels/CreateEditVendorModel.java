package com.southwicksstorage.southwicksstorage.models.formModels;

public class CreateEditVendorModel {

	private String vendorName;
	private String contactName;
	private String contactPhoneNumber;
	private String additionalInfo;
	
	public CreateEditVendorModel() {
		/* Default Constructor */
	}

	public CreateEditVendorModel(String vendorName, String contactName, String contactPhoneNumber, String additionalInfo) {
		this.vendorName = vendorName;
		this.contactName = contactName;
		this.contactPhoneNumber = contactPhoneNumber;
		this.additionalInfo = additionalInfo;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
}
