package com.diploma.backend.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.diploma.backend.repository.UserRepository;
import com.diploma.backend.validation.UniqueEmailConstraint;

public class UniqueEmailValidator implements
        ConstraintValidator<UniqueEmailConstraint, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository.existsByEmail(s)) {
            return false;
        }
        return true;
    }

}
