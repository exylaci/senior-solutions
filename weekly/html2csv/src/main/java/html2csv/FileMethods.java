package html2csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FileMethods {
    private List<Question> questions = new ArrayList<>();

    private WhereWeAre whereWeAre = WhereWeAre.OTHER;
    private Question question = new Question("");

    public static final String QUESTION_STARTS_WITH = "<h1 id=\"";
    public static final String ANSWERS_START_WITH = "<ul>";
    public static final String ANSWER_STARTS_WITH = "<li>";
    public static final String ANSWER_ENDS_WITH = "</li>";
    public static final String ANSWERS_ENDS_WITH = "</ul>";
    public static final String CORRECT_ANSWER_SIGN = "<input type=\"checkbox\" disabled=\"\" checked=\"\" />";

    private static final List<String> CUT_THESE = List.of(
            CORRECT_ANSWER_SIGN,
            ANSWERS_START_WITH,
            ANSWERS_ENDS_WITH,
            ANSWER_STARTS_WITH,
            ANSWER_ENDS_WITH,
            "</h1>",
            "<p>",
            "</p>",
            "</div>",
            "</span>",
            "</code>",
            "</pre>",
            "</a>"
    );

    private static final List<String> REMOVE_THESE = List.of(
            "<div class=",
            "<span id=",
            "<span class=",
            "<code class=",
            "<pre class=",
            "<a href="
    );

    private static final Map<String, String> REPLACE_THESE = Map.of(
            "&amp;", "&"
    );

    public void writeToFile(Path path) {
        if (questions.isEmpty()) {
            throw new IllegalStateException("There is nothing to export!");
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
            for (Question question : questions) {
                writer.write(question.getFullQuestion());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write to file: " + path, e);
        }
    }

    public void readFromFile(Path path) {
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.forEach(this::process);

        } catch (IOException e) {
            throw new IllegalStateException("Cannot read from file: " + path, e);
        }
    }

    private void process(String line) {
        if (line.contains(ANSWERS_ENDS_WITH)) {
            questions.add(question);
        }

        if (line.contains(QUESTION_STARTS_WITH) || whereWeAre == WhereWeAre.QUESTION) {
            processQuestion(line);
        }

        if (line.contains(ANSWER_STARTS_WITH) || whereWeAre == WhereWeAre.ANSWER) {
            processAnswer(line);
        }


    }

    private void processQuestion(String line) {
        if (whereWeAre == WhereWeAre.QUESTION) {
            question.appendQuestion(conversions(line));

        } else {

            int first = line.indexOf(QUESTION_STARTS_WITH) + 8;
            first = line.indexOf("\"", first) + 2;

            question = new Question(conversions(line.substring(first)));
        }

        if (line.contains(ANSWERS_START_WITH)) {
            whereWeAre = WhereWeAre.OTHER;
        } else {
            whereWeAre = WhereWeAre.QUESTION;
        }
    }

    private void processAnswer(String line) {
        if (whereWeAre == WhereWeAre.ANSWER) {
            question.appendToAnswer(conversions(line));
        } else {
            question.addANewAnswer(conversions(line));
        }

        if (line.contains(ANSWER_ENDS_WITH)) {
            whereWeAre = WhereWeAre.OTHER;
        } else {
            whereWeAre = WhereWeAre.ANSWER;
        }

        if (line.contains(CORRECT_ANSWER_SIGN)) {
            question.setCorrect();
        }
    }

    protected String conversions(String line) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(line);

        for (String eraseThis : CUT_THESE) {
            int start = stringBuilder.indexOf(eraseThis);
            while (start >= 0) {
                int end = start + eraseThis.length();
                stringBuilder.delete(start, end);
                start = stringBuilder.indexOf(eraseThis);
            }
        }
        for (String removeThis : REMOVE_THESE) {
            int start = stringBuilder.indexOf(removeThis);
            while (start >= 0) {
                int end = stringBuilder.indexOf("\">", start) + 2;
                stringBuilder.delete(start, end);
                start = stringBuilder.indexOf(removeThis);
            }
        }
        for (Map.Entry replaceThis : REPLACE_THESE.entrySet()) {
            int start = stringBuilder.indexOf(replaceThis.getKey().toString());
            while (start >= 0) {
                int end = start + replaceThis.getKey().toString().length();
                stringBuilder.replace(start, end, replaceThis.getValue().toString());
                start = stringBuilder.indexOf(replaceThis.getKey().toString());
            }
        }

        return stringBuilder.toString();
    }

    protected List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }
}