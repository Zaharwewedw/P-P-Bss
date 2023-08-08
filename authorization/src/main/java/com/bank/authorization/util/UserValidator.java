package com.bank.authorization.util;

import com.bank.authorization.model.Users;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> Aclazz) {
        return Users.class.equals(Aclazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
