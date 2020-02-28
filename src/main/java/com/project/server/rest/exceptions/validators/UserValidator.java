package com.project.server.rest.exceptions.validators;

import openapi.project.model.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;


public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;

        if(Objects.isNull(user.getEmail()) || !EmailValidator.getInstance().isValid(user.getEmail())) {
            errors.reject("email", "email is not valid");
        }
        if(Objects.isNull(user.getPassword()) || user.getPassword().isEmpty()) {
            errors.reject("password", "password is not valid");
        }
    }
}