/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.Util;

import com.expastudios.blogweb.annotations.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public final class UsernameValidator implements ConstraintValidator<Username, String> {


    private final String regexp = "^[a-z0-9_-]{3,12}$";

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches(regexp, input);
    }
}