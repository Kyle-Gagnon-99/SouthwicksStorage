package com.southwicksstorage.southwicksstorage.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

import com.southwicksstorage.southwicksstorage.constants.NotificationTypes;

public class NotificationTypeConverter implements AttributeConverter<NotificationTypes, String> {

	@Override
	public String convertToDatabaseColumn(NotificationTypes attribute) {
		if(attribute == null) {
			return null;
		}
		
		return attribute.getType();
	}

	@Override
	public NotificationTypes convertToEntityAttribute(String dbData) {
		
		if(dbData == null) {
			return null;
		}
		
		return Stream.of(NotificationTypes.values())
				.filter(c -> c.getType().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
