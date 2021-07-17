package nav;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class TimeValidatorTest {
    @Mock
    private ConstraintValidatorContext context;

    TimeValidator timeValidator = new TimeValidator();

//    @IsValidTime(attributum = 1848)
//    private Interval interval;
//
//    @Test
//    void init() throws NoSuchFieldException {
//        TimeValidator timeValidator = new TimeValidator();
//        timeValidator.initialize(TimeValidatorTest.class.getDeclaredField("attributum").getAnnotation(IsValidTime.class));
//    }

    @Test
    void isValidTestFuture() {
        assertTrue(timeValidator.isValid(
                new Interval(LocalDateTime.of(2041, 7, 16, 12, 23),
                        LocalDateTime.of(2042, 7, 16, 12, 23)),
                context));
    }

    @Test
    void isValidTestPast() {
        assertFalse(timeValidator.isValid(
                new Interval(LocalDateTime.of(1999, 7, 16, 12, 23),
                        LocalDateTime.of(2042, 7, 16, 12, 23)),
                context));
    }

    @Test
    void isValidTestNegativ() {
        assertThrows(IllegalAppointmentException.class, () ->
                timeValidator.isValid(
                        new Interval(LocalDateTime.of(2042, 7, 16, 12, 23),
                                LocalDateTime.of(2041, 7, 16, 12, 23)),
                        context));
    }
}