package com.aushev.autoriasearch.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExist {

    String message() default "User with this email already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
