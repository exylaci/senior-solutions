package html2csv;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileMethodsTest {

    @Test
    void conversionsCut() {
        assertEquals("abc", new FileMethods().conversions("</h1>a<p>b</span>c</span>"));

    }

    @Test
    void conversionsRemove() {
        assertEquals("abc", new FileMethods().conversions("<div class=\"anything\">a<span class=\"anything\">b<pre class=\"anything\">c<span class=\"anything\">"));
    }

    @Test
    void conversionsReplace() {
        assertEquals("&a&b&c&", new FileMethods().conversions("&amp;a&amp;b&amp;c&amp;"));
    }

    @Test
    void readFromFile() {
        FileMethods fileMethods = new FileMethods();
        fileMethods.readFromFile(Path.of("C:/training/senior-solutions/weekly/html2csv/src/main/resources/kerdesek.html"));

        List<Question> result = fileMethods.getQuestions();

        assertEquals(3, result.size());

        for (Question question : result) {
            for (String answer : question.getAnswers())
                assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing", answer);
        }

        assertEquals(2, result.get(0).getCorrect());
        assertEquals(4, result.get(1).getCorrect());
        assertEquals(2, result.get(2).getCorrect());

        assertTrue(result.get(1).getQuestion().contains("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."));
        assertTrue(result.get(2).getQuestion().contains("if (attacker.isAlive() && defender.isAlive()) {"));

    }
}