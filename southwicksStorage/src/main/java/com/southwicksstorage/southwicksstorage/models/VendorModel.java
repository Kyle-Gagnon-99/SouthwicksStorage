/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models;

/**
 * @author kyle
 *
 */
public class VendorModel {

	private int id;
	private String vendorName;
	private String contactName;
	private String contactPhoneNumber;
	private String additionalInfo;
	
	public VendorModel() {
		
	}
	
	public VendorModel(int id, String vendorName, String contactName, String contactPhoneNumber,
			String additionalInfo) {
		this.id = id;
		this.vendorName = vendorName;
		this.contactName = contactName;
		this.contactPhoneNumber = contactPhoneNumber;
		this.additionalInfo = additionalInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
