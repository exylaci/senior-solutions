package nav;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class TimeValidator implements ConstraintValidator<IsValidTime, Interval> {
//    private int attributum;

    @Override
    public boolean isValid(Interval interval, ConstraintValidatorContext context) {
        return interval.getBegin().isAfter(LocalDateTime.now());
    }
}
