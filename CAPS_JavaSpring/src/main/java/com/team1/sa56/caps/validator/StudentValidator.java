package com.team1.sa56.caps.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.team1.sa56.caps.model.Student;

@Component
public class StudentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.firstName", "First Name field is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.firstName", "Last Name field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email", "Email field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Password field is required.");
    }
    
}
