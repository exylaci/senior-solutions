package stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectToMap {
    public Map<String, String> original(List<String> strings) {
        Map<String, String> result = new HashMap<>();
        for (String word : strings) {
            result.merge(word.substring(0, 1), word, (prev, next) -> prev + next);
        }
        return result;
    }

    public Map<String, String> simplified(List<String> strings) {
        Map<String, String> result = new HashMap<>();
        strings.forEach(word -> result.merge(word.substring(0, 1), word, String::concat));
        return result;
    }

    public Map<String, String> withCollect(List<String> strings) {
        return strings
                .stream()
                .collect(Collectors.toMap(word -> word.substring(0, 1), Function.identity(), String::concat));
    }
}