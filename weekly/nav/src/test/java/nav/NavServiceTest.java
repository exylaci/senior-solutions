package nav;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NavServiceTest {

    @RepeatedTest(value = 13, name = "Adószám validátor teszt {currentRepetition}")
    void taxIdValidatorTest(RepetitionInfo repetitionInfo) {
        String[] source = {"1000000001", "0100000002", "0010000003", "0001000004", "0000100005", "0000010006", "0000001007", "0000000108", "0000000019",
                "0123456789", "1234567890", "123456789", "01234567890"};
        boolean[] result = {true, true, true, true, true, true, true, true, true,
                true, false, false, false};
        int round = repetitionInfo.getCurrentRepetition() - 1;
        assertEquals(result[round], new NavService(null).taxIdValidator(source[round]));
    }

    @RepeatedTest(value = 4, name = "Ügytípus validátor teszt {currentRepetition}")
    void cadeValidatorTest(RepetitionInfo repetitionInfo) {
        String[] source = {"", "1", "000", "001"};
        boolean[] result = {false, false, false, true};
        int round = repetitionInfo.getCurrentRepetition() - 1;
        assertEquals(result[round], new NavService(null).codeValidator(source[round]));
    }

}