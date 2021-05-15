/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.system;

import com.southwicksstorage.southwicksstorage.entities.StandEntity;

/**
 * @author kyle
 *
 */
public class StandLowMessage {

	private StandEntity stand;
	private String message;
	
	/**
	 * @param message
	 */
	public StandLowMessage(StandEntity stand) {
		this.setMessage(String.format("%s is low on stock", stand.getName()));
	}
	
	public StandEntity getStand() {
		return stand;
	}

	public void setStand(StandEntity stand) {
		this.stand = stand;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

}
