package nav;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TaxIdValidator.class)
public @interface IsValidTaxId {
    String message() default "invalid id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
