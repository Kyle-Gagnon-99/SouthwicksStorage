/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.formModels;

/**
 * @author kyle
 *
 */
public class CreateTypeOfStorageFormModel {
	
	private String name;
	private String additionalInfo;
	
	public CreateTypeOfStorageFormModel() {
		
	}
	
	public CreateTypeOfStorageFormModel(String name, String additionalInfo) {
		this.name = name;
		this.additionalInfo = additionalInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

}
