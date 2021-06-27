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
        assertEquals("\"a\r\nbc\"", new Question().setQuotes("a\r\nbc"));
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

    @Test
    void getFullQuestion() {
        Question question =new Question("kérdés");
        question.appendQuestion("2. sor");
        question.addANewAnswer("1. válasz");
        question.addANewAnswer("2. válasz");
        question.addANewAnswer("3. válasz");
        question.setCorrect();
        question.addANewAnswer("4. válasz");
        assertEquals("\"kérdés\r\n2. sor\";3;1. válasz;2. válasz;3. válasz;4. válasz",
                question.getFullQuestion());
    }
}