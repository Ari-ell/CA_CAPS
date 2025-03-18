package com.team1.sa56.caps.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.team1.sa56.caps.model.Admin;

@Component
public class AdminValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Admin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // Admin admin= (Admin) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.user.firstName.empty", "First Name field is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.user.lastName.empty", "Last Name field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email", "Email field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Password field is required.");
    }    
}
