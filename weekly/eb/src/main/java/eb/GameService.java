package eb;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameService {
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<Game> biggestGoalDifference() {
        return gameRepository.getGames()
                .stream()
                .max(Comparator.comparing(game -> Math.abs(game.getFirstCountryScore() - game.getSecondCountryScore())));
//                .map(game ->  Math.abs(game.getFirstCountryScore())- game.getSecondCountryScore());
    }

    public Integer numberOfGoals(String country) {
        return gameRepository.getGames()
                .stream()
                .mapToInt(game -> (game.getFirstCountry().equalsIgnoreCase(country) ? game.getFirstCountryScore() : 0) +
                        (game.getSecondCountry().equalsIgnoreCase(country) ? game.getSecondCountryScore() : 0))
                .sum();
    }

    public String mostGoalsOnTournament() {
        return gameRepository.getGames()
                .stream()
                .flatMap(game -> Stream.of(new Pairs(game.getFirstCountry(), game.getFirstCountryScore())
                        , new Pairs(game.getSecondCountry(), game.getSecondCountryScore())))
                .collect(Collectors.toMap(Pairs::getName, Pairs::getScore, Integer::sum))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(() -> new IllegalStateException("The list is empty"))
                .getKey();
    }

    private class Pairs {
        private String name;
        private int score;

        public Pairs(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}
