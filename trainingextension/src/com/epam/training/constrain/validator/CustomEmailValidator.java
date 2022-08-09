package com.epam.training.constrain.validator;

import com.epam.training.constrain.annotation.CustomEmail;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomEmailValidator implements ConstraintValidator<CustomEmail, String> {
    private static final Logger LOG = LoggerFactory.getLogger(CustomEmailValidator.class);

    private int minLength;
    private int maxLength;

    public CustomEmailValidator() {
    }

    @Override
    public void initialize(CustomEmail customEmail) {
        LOG.info("Initialize CustomEmail properties.");
        minLength = customEmail.minLength();
        LOG.info("minLength: {}", minLength);
        maxLength = customEmail.maxLength();
        LOG.info("maxLength: {}", maxLength);
        validateParameters();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        LOG.info("Start validation method");
        if (value == null) { // According to the JSR 303 null values are valid
            return true;
        }
        String regex = StringUtils.join("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{", minLength, ",", maxLength, "}");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    private void validateParameters()
    {
        if (isNegative(minLength)) {
            LOG.warn("The minLength parameter cannot be negative.");
            throw new IllegalArgumentException("The minLength parameter cannot be negative.");
        }
        if (isNegative(maxLength)) {
            LOG.warn("The maxLength parameter cannot be negative.");
            throw new IllegalArgumentException("The maxLength parameter cannot be negative.");
        }
        if (maxLength < minLength) {
            LOG.warn("The length cannot be negative.");
            throw new IllegalArgumentException("The length cannot be negative.");
        }
    }

    private boolean isNegative(Integer value) {
        return value < NumberUtils.INTEGER_ZERO;
    }
}
