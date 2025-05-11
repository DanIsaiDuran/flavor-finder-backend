package com.danduran.flavor_finder.validation;

import java.lang.annotation.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface UniqueUsername {
    String message() default "El nombre de usuario ya existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
