package com.team1.sa56.caps.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.team1.sa56.caps.model.Enrollment;

@Component
public class EnrollmentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Enrollment.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // Enrollment enrol= (Enrollment) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "student", "error.student", "Student Id field is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "course", "error.course", "Course Id field is required.");
    }
    
}
