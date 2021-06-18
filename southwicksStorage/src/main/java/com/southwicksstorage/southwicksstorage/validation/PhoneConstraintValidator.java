package com.southwicksstorage.southwicksstorage.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone arg0) {
    }

	@Override
    public boolean isValid(String checkPhoneNumber, ConstraintValidatorContext context) {
		
		if(StringUtils.isEmpty(checkPhoneNumber)) {
			return true;
		}
    	
    	PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    	PhoneNumber phoneNumber = null;
		try {
			phoneNumber = phoneNumberUtil.parse(checkPhoneNumber, "US");
		} catch (NumberParseException e) {
			return false;
		}
    	boolean resultIsPossible = phoneNumberUtil.isPossibleNumber(phoneNumber);
    	
    	if(StringUtils.isEmpty(checkPhoneNumber)) {
    		return false;
    	}
    	
    	if(resultIsPossible) {
    		return true;
    	} else {
    		return false;
    	}
    	
    }
}
