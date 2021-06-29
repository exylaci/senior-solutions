package training360.bikes;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BikeService {
    public static final String SEPARATOR = ";";
    public static final DateTimeFormatter fomat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Path path = Path.of("bikes.csv");

    private List<Bike> bikes = new ArrayList<>();

    public List<Bike> getBikes() {
        if (bikes.isEmpty()) {
            loadBikes();
        }
        return bikes;
    }

    public List<String> getUsers() {
        if (bikes.isEmpty()) {
            loadBikes();
        }
        return bikes
                .stream()
                .map(Bike::getUserId)
                .distinct()
                .toList();
    }

    protected void loadBikes() {
        bikes = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream
                    .filter(line -> !line.isBlank())
                    .map(line -> line.split(SEPARATOR))
                    .filter(parts -> parts.length > 3)
                    .map(parts -> new Bike(
                            parts[0],
                            parts[1],
                            LocalDateTime.parse(parts[2], fomat),
                            Double.parseDouble(parts[3])))
                    .forEach(bikes::add);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read from csv file", e);
        }
    }
}
