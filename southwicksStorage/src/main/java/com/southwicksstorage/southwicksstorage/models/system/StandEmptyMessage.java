/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.system;

import com.southwicksstorage.southwicksstorage.entities.StandEntity;

/**
 * @author kyle
 *
 */
public class StandEmptyMessage {

	private StandEntity stand;
	private String message;
	
	public StandEmptyMessage(StandEntity stand) {
		this.stand = stand;
		this.setMessage(String.format("%s is out of stock. You can view the stands stock at the view section", stand.getName()));
	}
	
	public void setStand(StandEntity stand) {
		this.stand = stand;
	}
	
	public StandEntity getStand() {
		return stand;
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
