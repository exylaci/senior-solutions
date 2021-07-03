package fleamarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FleamarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleamarketApplication.class, args);
    }
}