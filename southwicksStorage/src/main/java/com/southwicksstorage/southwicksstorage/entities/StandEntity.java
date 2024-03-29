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

import org.hibernate.envers.Audited;

import com.southwicksstorage.southwicksstorage.constants.Constants;

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "stand")
@Audited
public class StandEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotEmpty(message = "Stand name can not be empty")
	@Column(name = "name")
	private String name;
	
	@Size(max = 500, message = Constants.ADDITIONAL_INFO_EXCEED)
	@Column(name = "additional_info")
	private String additionalInfo;
	
	public StandEntity() {
		/* Default Constructor */
	}

	public StandEntity(@NotEmpty(message = "Stand name can not be empty") String name,
			@Size(max = 500, message = "Additional Information can not exceed 500 characters") String additionalInfo) {
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
