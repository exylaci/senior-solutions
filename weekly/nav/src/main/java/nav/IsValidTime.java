package nav;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TimeValidator.class)
public @interface IsValidTime {
    String message() default "invalid time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
