package com.diploma.backend.validation.validators;

import com.diploma.backend.repository.UserRepository;
import com.diploma.backend.validation.UniqueUsernameConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements
        ConstraintValidator<UniqueUsernameConstraint, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository.existsByUsername(s)) {
            return false;
        }
        return true;
    }
}
