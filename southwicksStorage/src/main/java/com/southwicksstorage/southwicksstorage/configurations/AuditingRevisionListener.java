/**
 * 
 */
package com.southwicksstorage.southwicksstorage.configurations;

import java.util.Date;
import java.util.Optional;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.entities.AuditedRevisionEntity;
import com.southwicksstorage.southwicksstorage.models.CustomUserDetails;

/**
 * @author kyle
 *
 */
@Component
public class AuditingRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		AuditedRevisionEntity revEntity = (AuditedRevisionEntity) revisionEntity;
		Date dateModified = new Date();
		String username = null;
		
		Optional<CustomUserDetails> userDetails = Optional.ofNullable(SecurityContextHolder.getContext())
									            .map(SecurityContext::getAuthentication)
									            .filter(Authentication::isAuthenticated)
									            .map(Authentication::getPrincipal)
									            .map(CustomUserDetails.class::cast);
		
		if(userDetails.isPresent()) {
			username = userDetails.get().getUsername();
		} else {
			username = Constants.SYSTEM_USERNAME;
		}
		
		revEntity.setUsername(username);
		revEntity.setDateModified(dateModified);
		
	}

}
