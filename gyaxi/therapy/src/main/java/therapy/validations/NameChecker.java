package therapy.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameChecker implements ConstraintValidator<IsValidName, String> {
    private int minLength;

    @Override
    public void initialize(IsValidName constraintAnnotation) {
        minLength = constraintAnnotation.minLength();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null &&
                value.length() >= minLength &&
                value.contains(" ");
    }
}