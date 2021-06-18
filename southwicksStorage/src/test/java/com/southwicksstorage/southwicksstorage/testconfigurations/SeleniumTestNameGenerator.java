/**
 * 
 */
package com.southwicksstorage.southwicksstorage.testconfigurations;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayNameGenerator;

/**
 * @author kyle
 *
 */
public class SeleniumTestNameGenerator implements DisplayNameGenerator {

	@Override
	public String generateDisplayNameForClass(Class<?> testClass) {
		String[] splitClassName = StringUtils.splitByCharacterTypeCamelCase(testClass.getSimpleName());
		StringBuilder buildString = new StringBuilder();
		
		for(String splitString : splitClassName) {
			buildString.append(splitString + " ");
		}
		
		return buildString.toString();
	}

	@Override
	public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
		String[] splitClassName = StringUtils.splitByCharacterTypeCamelCase(nestedClass.getSimpleName());
		StringBuilder buildString = new StringBuilder();
		
		for(String splitString : splitClassName) {
			buildString.append(splitString + " ");
		}
		
		return buildString.toString();
	}

	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		String[] splitMethodName = StringUtils.splitByCharacterTypeCamelCase(testMethod.getName());
		StringBuilder buildString = new StringBuilder();
		
		for(int index = 0; index < splitMethodName.length; index++) {
			if(index == 0) {
				buildString.append(StringUtils.capitalize(splitMethodName[index].toLowerCase()) + " ");
			} else {
				buildString.append(splitMethodName[index].toLowerCase() + " ");
			}
		}
		
		return buildString.toString();
	}

}
