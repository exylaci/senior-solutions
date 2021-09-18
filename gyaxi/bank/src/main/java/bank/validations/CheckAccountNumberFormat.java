package bankremake.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AccountNumberFormatChecker.class)
public @interface CheckAccountNumberFormat {
    String message() default "It contains too few words!";

    int exactlength() default 8;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}