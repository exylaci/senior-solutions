package bankremake.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountNumberFormatChecker implements ConstraintValidator<CheckAccountNumberFormat, String> {
    private int exactLength;

    @Override
    public void initialize(CheckAccountNumberFormat constraintAnnotation) {
        exactLength = constraintAnnotation.exactlength();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null &&
                value.length() == exactLength &&
                value.matches("[0-9]{8}");
    }
}