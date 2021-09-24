package mentortools.trainingclass;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<IsValidDates, BeginEndDates> {
    @Override
    public boolean isValid(BeginEndDates dates, ConstraintValidatorContext context) {
        return dates.getEnd().isAfter(dates.getBegin());
    }
}