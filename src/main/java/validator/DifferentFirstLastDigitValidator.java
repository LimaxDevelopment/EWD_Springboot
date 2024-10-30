package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentFirstLastDigitValidator implements ConstraintValidator<DifferentFirstLastDigit, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() != 5) {
            return false;
        }
        return value.charAt(0) != value.charAt(value.length() - 1);
    }
}

