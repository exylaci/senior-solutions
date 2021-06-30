package eb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryTest {
    GameRepository gameRepository = new GameRepository();
    Game game = new Game("winer", "looser", 1, 0);
    static final String EMPTY = null;

    @Test
    void addGame() {
        gameRepository.addGame(game);

        assertEquals(game, gameRepository.getGames().get(0));
    }

    @Test
    void addGameNullGame() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                gameRepository.addGame(null));
    }

    @Test
    void addGameNullName1() {
        game.setFirstCountry(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                gameRepository.addGame(null));
    }

    @Test
    void addGameNullName2() {
        game.setSecondCountry(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                gameRepository.addGame(null));
    }

    @Test
    void addGameFromFile() {
        gameRepository.addGameFromFile("src/main/resources/results.csv");
        List<Game> result = gameRepository.getGames();

        assertEquals(15, result.size());
        assertEquals("Turkey", result.get(0).getFirstCountry());
        assertEquals("North Macedonia", result.get(5).getSecondCountry());
        assertEquals(1, result.get(12).getSecondCountryScore());
        assertEquals(2, result.get(14).getFirstCountryScore());

        assertIterableEquals(List.of(
                "Turkey",
                "Wales",
                "Denmark",
                "Belgium",
                "England",
                "Austria",
                "Netherlands",
                "Scotland",
                "Poland",
                "Spain",
                "Hungary",
                "French",
                "Finnland",
                "Turkey",
                "Italy"
        ), result.stream().map(Game::getFirstCountry).toList());

        asserrtLinesMatc  ch(".+",result.stream().map(Game::getSecondCountry).toList());
    }
}