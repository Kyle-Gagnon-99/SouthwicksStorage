/**
 * 
 */
package com.southwicksstorage.southwicksstorage.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;

/**
 * @author kyle
 *
 */
@Entity
@Table(name = "system_settings")
public class SystemSettingsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@NotNull(message = "System settings name can not be empty")
	@Column(name = "settings_name")
	private SystemSettingsName settingsName;
	
	@NotNull(message = "System settings value can not be empty")
	@Column(name = "settings_value")
	private String settingsValue;
	
	public SystemSettingsEntity() {
		/* Default Constructor */
	}
	
	public SystemSettingsEntity(SystemSettingsName settingsName, String settingsValue) {
		this.settingsName = settingsName;
		this.settingsValue = settingsValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SystemSettingsName getSettingsName() {
		return settingsName;
	}

	public void setSettingsName(SystemSettingsName settingsName) {
		this.settingsName = settingsName;
	}

	public String getSettingsValue() {
		return settingsValue;
	}

	public void setSettingsValue(String settingsValue) {
		this.settingsValue = settingsValue;
	}
	
}
