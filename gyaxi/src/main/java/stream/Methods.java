package stream;

import java.util.List;
import java.util.stream.Stream;

public class Methods {
    public static void main(String[] args) {
        Stream stream = Stream.of("egy", "kettő", "három", "négy", "öt", "hat", "hét", "nyolc", "kilenc", "tíz");
        List<String> list = stream.toList();

        Stream.iterate(1, i -> i + 2)
                .limit(10 / 2)
                .map(list::get)
                .forEach(System.out::print);
    }
}
