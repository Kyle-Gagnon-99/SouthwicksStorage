/**
 * 
 */
package com.southwicksstorage.southwicksstorage.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

import com.southwicksstorage.southwicksstorage.constants.StorageType;

/**
 * @author kyle
 *
 */
public class StorageTypeConverter implements AttributeConverter<StorageType, String> {

	@Override
	public String convertToDatabaseColumn(StorageType attribute) {
		if(attribute == null) {
			return null;
		}
		
		return attribute.getStorageTypeName();
	}

	@Override
	public StorageType convertToEntityAttribute(String dbData) {
		if(dbData == null) {
			return null;
		}
		
		return Stream.of(StorageType.values())
				.filter(c -> c.getStorageTypeName().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
