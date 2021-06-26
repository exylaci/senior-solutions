package html2csv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Question {
    private String question;
    private int correct;
    private List<String> answers = new ArrayList<>();

    private static final String SEPARATOR = ";";

    private static final String ANY_PIECES_OF_QUOTES = "\"+";
    private static final String TWO_QUOTES = "\"\"";
    private static final String ONE_QUOTES = "\"";
    private static final String LINE_FEED = "\n\r";

    public Question(String question) {
        this.question = question;
    }

    protected Question() {
    }

    public void appendQuestion(String question) {
        if (!this.question.isBlank() && !question.isBlank()) {
            this.question += LINE_FEED;
        }
        this.question += question;
    }

    public void setCorrect() {
        correct = answers.size();
    }

    public void addANewAnswer(String answer) {
        answers.add(answer);
    }

    public void appendToAnswer(String answer) {
        String lastAnswer;
        if (answers.isEmpty()) {
            lastAnswer = "";
        } else {
            lastAnswer = answers.get(answers.size() - 1);
        }
        if (!lastAnswer.isBlank() && !answer.isBlank()) {
            lastAnswer += LINE_FEED;
        }
        lastAnswer += answer;

        answers.set(answers.size() - 1, lastAnswer);
    }

    public String getFullQuestion() {
        return setQuotes(question) + SEPARATOR + correct + SEPARATOR +
                answers.stream().map(this::setQuotes).collect(Collectors.joining(SEPARATOR));
    }

    protected String setQuotes(String text) {
        if (text.contains(ONE_QUOTES) || text.contains(LINE_FEED) || text.contains(";")) {
            return '"' + text.replaceAll(ANY_PIECES_OF_QUOTES, TWO_QUOTES) + '"';
        }
        return text;
    }


    protected String getQuestion() {
        return question;
    }

    protected List<String> getAnswers() {
        return new ArrayList<>(answers);
    }

    protected int getCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        return "question='" + question + "'\n\r correct answer=" + correct +
                answers.stream().map(answer -> "\n\r answers=" + answer).collect(Collectors.joining()) +
                "\n\r";
    }
}