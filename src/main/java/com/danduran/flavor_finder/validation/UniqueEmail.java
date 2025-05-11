package com.danduran.flavor_finder.validation;

import java.lang.annotation.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
     String message() default "El email ya esta en uso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
