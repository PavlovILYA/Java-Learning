package com.mephi.hm.validators;

import com.mephi.hm.exceptions.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements Validator {
    @Override
    public void validate(String value) throws ValidatorException {
        Pattern pattern = Pattern.compile("\\d{11}");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.find()) {
            throw new ValidatorException("Неверный формат номера");
        }
    }
}