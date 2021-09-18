package bankremake.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TargetAccountNumberChecker.class)
public @interface CheckIfItIsNeeded {
    String message() default "It contains too few words!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}