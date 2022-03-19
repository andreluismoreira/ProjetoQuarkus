package com.andre.cadastro.Exception;

import javax.validation.ConstraintValidatorContext;

public interface DTO {

    default boolean isValid(ConstraintValidatorContext constraintValidatorContext){
        return true;
    }
}
