/**
 * 
 */
package com.southwicksstorage.southwicksstorage.constants;

/**
 * @author kyle
 *
 */
public enum SystemSettingsName {
	
	LOW_THRESHOLD("Notification Low Threshold"),
	OUT_THRESHOLD("Notification Out Threshold");
	
	private String settingsName;
	
	SystemSettingsName(String settingsName) {
		this.settingsName = settingsName;
	}
	
	public String getSettingsName() {
		return settingsName;
	}

}
