/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.annotations;

import com.expastudios.blogweb.Util.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {
    String message() default "Invalid Username: It required \\t 3-12 length, lowerCase, upperCase. It can contain hyphen, underscore";

    //String regexp() default ".*/";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
