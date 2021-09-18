package bankremake.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TargetAccountNumberChecker implements ConstraintValidator<CheckIfItIsNeeded, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ||
                (value.length() == 8 &&
                        value.matches("[0-9]{8}"));
    }
}