package eb;

import java.util.Optional;

public class Game {
    private String firstCountry;
    private String secondCountry;
    private int firstCountryScore;
    private int secondCountryScore;

    public Game() {
    }

    public Game(String firstCountry, String secondCountry, int firstCountryScore, int secondCountryScore) {
        this.firstCountry = firstCountry;
        this.secondCountry = secondCountry;
        this.firstCountryScore = firstCountryScore;
        this.secondCountryScore = secondCountryScore;
    }

    public String getFirstCountry() {
        return firstCountry;
    }

    public void setFirstCountry(String firstCountry) {
        this.firstCountry = firstCountry;
    }

    public String getSecondCountry() {
        return secondCountry;
    }

    public void setSecondCountry(String secondCountry) {
        this.secondCountry = secondCountry;
    }

    public int getFirstCountryScore() {
        return firstCountryScore;
    }

    public void setFirstCountryScore(int firstCountryScore) {
        this.firstCountryScore = firstCountryScore;
    }

    public int getSecondCountryScore() {
        return secondCountryScore;
    }

    public void setSecondCountryScore(int secondCountryScore) {
        this.secondCountryScore = secondCountryScore;
    }

    public Optional<String> getWinner() {
        if (firstCountryScore > secondCountryScore) {
            return Optional.of(firstCountry);
        }
        if (firstCountryScore < secondCountryScore) {
            return Optional.of(secondCountry);
        }
        return Optional.empty();
    }
}
