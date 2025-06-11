package com.cisu.store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// if we want to apply to multi type we add then inside "{}"
@Target({ElementType.FIELD})
// Retention specify where the annotation is applied
@Retention(RetentionPolicy.RUNTIME)
// link the class
@Constraint(validatedBy = LowercaseValidator.class)
public @interface Lowercase {
    String message() default "must be lowercase";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
