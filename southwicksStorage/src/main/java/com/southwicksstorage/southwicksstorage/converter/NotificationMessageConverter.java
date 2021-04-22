package com.southwicksstorage.southwicksstorage.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

import com.southwicksstorage.southwicksstorage.constants.NotificationMessages;

public class NotificationMessageConverter implements AttributeConverter<NotificationMessages, String> {

	@Override
	public String convertToDatabaseColumn(NotificationMessages attribute) {
		if(attribute == null) {
			return null;
		}
		
		return attribute.getMessage();
	}

	@Override
	public NotificationMessages convertToEntityAttribute(String dbData) {
		if(dbData == null) {
			return null;
		}
		
		return Stream.of(NotificationMessages.values())
				.filter(c -> c.getMessage().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
