package com.team1.sa56.caps.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.team1.sa56.caps.model.Lecturer;

@Component
public class LecturerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Lecturer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.firstName", "First Name field is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.lastName", "Last Name field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email", "Email field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Password field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.description", "Description field is required.");
    }
    
}
