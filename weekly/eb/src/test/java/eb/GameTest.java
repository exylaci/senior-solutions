package eb;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @ParameterizedTest
    @MethodSource("createGames")
    void getWinner(Game game, Optional<String> winner) {
        assertEquals(winner, game.getWinner());
    }

    static Stream<Arguments> createGames() {
        return Stream.of(
                Arguments.arguments(new Game("first", "second", 1, 0), Optional.of("first")),
                Arguments.arguments(new Game("first", "second", 0, 1), Optional.of("second")),
                Arguments.arguments(new Game("first", "second", 1, 1), Optional.empty()));
    }


//    static final Game game1 = new Game("first", "second", 1, 0);
//    static final Game game2 = new Game("first", "second", 0, 1);
//    static final Game game3 = new Game("first", "second", 1, 1);
//    static final Optional<String> winner1 = Optional.of("first");
//    static final Optional<String> winner2 = Optional.of("second");
//    static final Optional<String> winner3 = Optional.empty();
//
//    @ParameterizedTest
//    @ValueSource(values = {
//            {game1, winner1},
//            {game2, winner2},
//            {game3, winner3}})
//    @Test
//    void getWinner2(Game game, Optional<String> winner) {
//        assertEquals(winner, game.getWinner());
//    }
}