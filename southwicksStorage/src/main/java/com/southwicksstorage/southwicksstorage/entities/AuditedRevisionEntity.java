/**
 * 
 */
package com.southwicksstorage.southwicksstorage.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.southwicksstorage.southwicksstorage.configurations.AuditingRevisionListener;
import com.southwicksstorage.southwicksstorage.constants.Constants;

/**
 * @author kyle
 *
 */
@Entity
@EntityScan(basePackages = {"com.southwicksstorage.southwicksstorage.entities", "com.southwicksstorage.southwicksstorage.envers.entity"})
@RevisionEntity(AuditingRevisionListener.class)
public class AuditedRevisionEntity extends DefaultRevisionEntity {
	
	private static final long serialVersionUID = -1175688867820627863L;
	private String username;
	private String dateModified;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the dateModified
	 */
	public String getDateModified() {
		return dateModified;
	}

	/**
	 * @param dateModified the dateModified to set
	 */
	public void setDateModified(Date _temp) {
		LocalDateTime currentTime = LocalDateTime.now();
		ZonedDateTime currentTimeEast = currentTime.atZone(ZoneId.of("America/New_York"));
		
		this.dateModified = Constants.formatter.format(currentTimeEast);
	}
	
}
