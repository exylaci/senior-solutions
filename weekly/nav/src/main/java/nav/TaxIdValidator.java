package nav;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class TaxIdValidator implements ConstraintValidator<IsValidTaxId, String> {

    private NavService service;

    public TaxIdValidator(NavService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String taxId, ConstraintValidatorContext context) {
        return service.taxIdValidator(taxId);
    }

//    @Override
//    public void initialize(Annotation constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
}
