/**
 * 
 */
package com.southwicksstorage.southwicksstorage.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

import com.southwicksstorage.southwicksstorage.constants.SystemSettingsName;

/**
 * @author kyle
 *
 */
public class SystemSettingsNameConverter implements AttributeConverter<SystemSettingsName, String> {

	@Override
	public String convertToDatabaseColumn(SystemSettingsName attribute) {
		if(attribute == null) {
			return null;
		}
		
		return attribute.getSettingsName();
	}

	@Override
	public SystemSettingsName convertToEntityAttribute(String dbData) {
		if(dbData == null) {
			return null;
		}
		
		return Stream.of(SystemSettingsName.values())
				.filter(c -> c.getSettingsName().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
