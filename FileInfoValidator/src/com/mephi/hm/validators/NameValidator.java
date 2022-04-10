package com.mephi.hm.validators;

import com.mephi.hm.exceptions.ValidatorException;


public class NameValidator implements Validator {
    @Override
    public void validate(String value) throws ValidatorException {
        if (value.length() > 30) {
            throw new ValidatorException("В ФИО больше 30 букв");
        }
    }
}

