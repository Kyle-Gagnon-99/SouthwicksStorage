package com.southwicksstorage.southwicksstorage.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import com.southwicksstorage.southwicksstorage.validation.Phone;

@Entity
@Table(name = "vendor")
@Audited
public class VendorEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "Vendor name can not be empty")
	@Column(name = "vendor_name")
	private String vendorName;
	
	@NotEmpty(message = "Contact name can not be empty")
	@Column(name = "contant_name")
	private String contactName;
	
	@Phone
	@Column(name = "contant_phone_number")
	private String contactPhoneNumber;
	
	@Size(max = 500, message = "Additional Information can not exceed 500 characters")
	@Column(name = "additional_info")
	private String additionalInfo;
	
	public VendorEntity() {
		/* Default Constructor */
	}
	
	public VendorEntity(String vendorName, String contactName, String contactPhoneNumber, String additionalInfo) {
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
