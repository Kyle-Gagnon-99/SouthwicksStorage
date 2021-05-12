/**
 * 
 */
package com.southwicksstorage.southwicksstorage.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.southwicksstorage.southwicksstorage.configurations.AuditingRevisionListener;

/**
 * @author kyle
 *
 */
@Entity
@EntityScan(basePackages = {"com.southwicksstorage.southwicksstorage.entities","com.southwicksstorage.southwicksstorage.envers.entity"})
@RevisionEntity(AuditingRevisionListener.class)
public class AuditedRevisionEntity extends DefaultRevisionEntity {
	
	private static final long serialVersionUID = -1175688867820627863L;
	private String username;
	private Date dateModified;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the dateModified
	 */
	public Date getDateModified() {
		return dateModified;
	}

	/**
	 * @param dateModified the dateModified to set
	 */
	public void setDateModified(Date dateModified) {
		String dateToMod = dateModified.toString();
		SimpleDateFormat isoFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		isoFormat.setTimeZone(TimeZone.getTimeZone("EST"));
		try {
			this.dateModified = isoFormat.parse(dateToMod);
		} catch (ParseException e) {
			this.dateModified = dateModified;
		}
	}
	
}
