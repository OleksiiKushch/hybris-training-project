package com.epam.training.constrain.annotation;

import com.epam.training.constrain.validator.CustomEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {CustomEmailValidator.class}
)
@Documented
public @interface CustomEmail {
    String message() default "{com.epam.training.constrain.annotation.CustomEmail.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minLength() default 3;
    int maxLength() default 8;
}
