package eb;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameRepository {
    public static final String SEPARATOR = ";";
    private List<Game> games = new ArrayList<>();

    public List<Game> getGames() {
        return new ArrayList<>(games);
    }

    public void addGame(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Game is a must!");
        }
        games.add(game);
    }

    public void addGameFromFile(String path) {
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            stream
                    .filter(line -> line.contains(SEPARATOR))
                    .map(line -> line.split(SEPARATOR))
                    .map(parts -> new Game(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])))
                    .forEach(this::addGame);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file!", e);
        }

//        try (
//                BufferedReader bf = new BufferedReader(new InputStreamReader(GameRepository.class.getResourceAsStream("/results.csv")))) {
//            String line;
//            while ((line = bf.readLine()) != null) {
//                String[] array = line.split(";");
//                addGame(new Game(array[0], array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3])));
//            }
//        } catch (
//                IOException ioe) {
//            throw new IllegalStateException("Can not read file", ioe);
//        }
    }
}
