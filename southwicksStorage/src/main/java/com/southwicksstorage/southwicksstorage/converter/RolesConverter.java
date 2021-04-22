package com.southwicksstorage.southwicksstorage.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

import com.southwicksstorage.southwicksstorage.constants.Roles;

public class RolesConverter implements AttributeConverter<Roles, String> {

	@Override
	public String convertToDatabaseColumn(Roles attribute) {
		if(attribute == null) {
			return null;
		}
		
		return attribute.getRole();
	}

	@Override
	public Roles convertToEntityAttribute(String dbData) {
		if(dbData == null) {
			return null;
		}
		
		return Stream.of(Roles.values())
				.filter(c -> c.getRole().equals(dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
