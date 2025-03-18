package com.team1.sa56.caps.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.team1.sa56.caps.model.Course;

@Component
public class CourseValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Course.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Course course = (Course) target;
        if ((course.getStartDate() != null && course.getEndDate() != null) &&
        (course.getStartDate().compareTo(course.getEndDate()) > 0)) {
            errors.rejectValue("endDate", "error.dates", "End Date must be later than Start Date");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "Name field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.description", "Description field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capacity", "error.capacity", "Capacity field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "credits", "error.credits", "Credits field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "error.startDate", "Start Date field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "error.endDate", "End Date field is required.");
    }

}
