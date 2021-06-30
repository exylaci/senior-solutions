package eb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameService gameService;

    @Test
    void biggestGoalDifferenceListIsEpty() {
        when(gameRepository.getGames()).thenReturn(Collections.emptyList());
        assertEquals(Optional.empty(), gameService.biggestGoalDifference());
    }

    @Test
    void biggestGoalDifferenceListIsNotEmptyButAllIsDraw() {
        when(gameRepository.getGames()).thenReturn(Arrays.asList(
                new Game("HUN", "GER", 0, 0),
                new Game("HUN", "GER", 2, 2)
        ));
        Game game = new Game();
        if (gameService.biggestGoalDifference().isPresent()) {
            game = gameService.biggestGoalDifference().get();
        } else {
            fail();
        }

        assertEquals(0, Math.abs(game.getFirstCountryScore() - game.getSecondCountryScore()));
    }

    @Test
    void biggestGoalDifferenceList() {
        when(gameRepository.getGames()).thenReturn(Arrays.asList(
                new Game("HUN", "GER", 5, 0),
                new Game("HUN", "GER", 0, 5)
        ));
        Game game = gameService.biggestGoalDifference().get();

        assertEquals(5, Math.abs(game.getFirstCountryScore() - game.getSecondCountryScore()));
    }

    @ParameterizedTest
    @MethodSource("countryGoals")
    void test(String country, int goals) {
        when(gameRepository.getGames()).thenReturn(Arrays.asList(
                new Game("HUN", "GER", 1, 5),
                new Game("HUN", "GER", 0, 9),
                new Game("BRA", "GER", 0, 0),
                new Game("POR", "GER", 0, 1),
                new Game("HUN", "POR", 0, 0),
                new Game("POL", "HUN", 0, 1)
        ));
        assertEquals(goals, gameService.numberOfGoals(country));
    }

    public static Stream<Arguments> countryGoals() {
        return Stream.of(
                Arguments.arguments("HUN", 2),
                Arguments.arguments("GER", 15)

        );
    }

    @Test
    void mostGoalsOnTournament() {
        GameRepository gameRepository = new GameRepository();
        gameRepository.addGame(new Game("HUN", "POR", 0, 7));
        gameRepository.addGame(new Game("POR", "GER", 7, 3));
        gameRepository.addGame(new Game("HUN", "GER", 0, 8));

        GameService gameService = new GameService(gameRepository);

        assertEquals("POR", gameService.mostGoalsOnTournament());
    }
}