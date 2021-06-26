package html2csv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    // abc --> abc
    @Test
    void replaceQuotesNone() {
        assertEquals("abc", new Question().setQuotes("abc"));
    }

    // a <linefeed> bc --> "a <linefeed> bc"
    @Test
    void replaceQuotesLineFeed() {
        assertEquals("\"a\n\rbc\"", new Question().setQuotes("a\n\rbc"));
    }

    // a""b"c --> "a""b""c"
    @Test
    void replaceQuotesSome() {
        assertEquals("\"a\"\"b\"\"c\"", new Question().setQuotes("a\"\"b\"c"));
    }

    // "abc" --> """abc"""
    @Test
    void replaceQuotesAtStartEnd() {
        assertEquals("\"\"\"abc\"\"\"", new Question().setQuotes("\"abc\""));
    }

}