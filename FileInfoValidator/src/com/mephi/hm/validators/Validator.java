package com.mephi.hm.validators;

import com.mephi.hm.exceptions.ValidatorException;

public interface Validator {
    void validate(String value) throws ValidatorException;
}
