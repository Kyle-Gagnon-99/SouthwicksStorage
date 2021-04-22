package com.southwicksstorage.southwicksstorage.validation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.util.ResourceUtils;

public class ValidatePassword {
	
	private static String errorMessage = "No errors";

	public static boolean isPasswordValid(String password) throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(ResourceUtils.getFile("classpath:message.properties")));
		MessageResolver resolver = new PropertiesMessageResolver(props);
		
        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
                // at least 8 characters
                new LengthRule(8, 30),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // no whitespace
                new WhitespaceRule()

            ));
            RuleResult result = validator.validate(new PasswordData(password));
            if (result.isValid()) {
                return true;
            }
            
            if(!result.isValid()) {
                List<String> messages = validator.getMessages(result);

                errorMessage = messages.stream().collect(Collectors.joining(", "));
            }

            return false;
	}
	
	public static String getErrorMessage() {
		return errorMessage;
	}
	
}
