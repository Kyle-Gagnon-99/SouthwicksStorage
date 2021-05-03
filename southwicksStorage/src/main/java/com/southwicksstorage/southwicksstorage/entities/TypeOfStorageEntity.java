/**
 * 
 */
package com.southwicksstorage.southwicksstorage.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.southwicksstorage.southwicksstorage.constants.Constants;

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "type_of_storage")
public class TypeOfStorageEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "Name of storage type can not be empty")
	@Column(name = "name")
	private String name;
	
	@Size(max = 500, message = Constants.ADDITIONAL_INFO_EXCEED)
	@Column(name = "additional_info")
	private String additionalInfo;
	
	public TypeOfStorageEntity() {
		
	}

	public TypeOfStorageEntity(int id, @NotEmpty(message = "Name of storage type can not be empty") String name,
			@Size(max = 500, message = "Additional Information can not exceed 500 characters") String additionalInfo) {
		super();
		this.id = id;
		this.name = name;
		this.additionalInfo = additionalInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
