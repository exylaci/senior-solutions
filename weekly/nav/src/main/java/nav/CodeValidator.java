package nav;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodeValidator implements ConstraintValidator<IsValidCode, String> {

    private NavService service;

    public CodeValidator(NavService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return service.codeValidator(code);
    }
}
