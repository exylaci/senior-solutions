package fleamarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Status;
import org.zalando.problem.Problem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdvertisementControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() throws InterruptedException {
        template.delete("/fleamarket/advertisement/all");

        template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(LumberCategory.FESTMÉNY, "Első festmény."),
                AdvertisementDTO.class);
        Thread.sleep(1);
        template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(LumberCategory.FESTMÉNY, "Második festmény."),
                AdvertisementDTO.class);
        Thread.sleep(1);
        template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(LumberCategory.BÚTOR, "Első bútor."),
                AdvertisementDTO.class);
        Thread.sleep(1);
        template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(LumberCategory.FESTMÉNY, "Harmadik festmény."),
                AdvertisementDTO.class);
        Thread.sleep(1);
        template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(LumberCategory.BÚTOR, "Második bútor."),
                AdvertisementDTO.class);
        Thread.sleep(1);
        template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(LumberCategory.BÚTOR, "Harmadik bútor."),
                AdvertisementDTO.class);
        Thread.sleep(1);
        template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(LumberCategory.MŰSZAKICIKK, "Nem a második műszaki cikk."),
                AdvertisementDTO.class);
    }

    @Test
    void getAllItem() {
        template.put("/fleamarket/advertisement/4",
                new UpdateAdvertisementCommand("modified"),
                AdvertisementDTO.class);

        List<AdvertisementDTO> result = template.exchange(
                "/fleamarket/advertisement",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdvertisementDTO>>() {
                }).getBody();

        assertThat(result)
                .hasSize(7)
                .extracting(AdvertisementDTO::getLumberCategory)
                .contains(LumberCategory.BÚTOR, LumberCategory.FESTMÉNY, LumberCategory.MŰSZAKICIKK);

        assertEquals("modified",result.get(0).getText());
        assertEquals("Második bútor.",result.get(3).getText());
        assertEquals("Első festmény.",result.get(6).getText());
    }

    @Test
    void getByCategory() {
        List<AdvertisementDTO> result = template.exchange(
                "/fleamarket/advertisement?category=bútor",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdvertisementDTO>>() {
                }).getBody();

        assertThat(result)
                .hasSize(3)
                .extracting(AdvertisementDTO::getLumberCategory)
                .contains(LumberCategory.BÚTOR);
    }

    @Test
    void getByText() {
        List<AdvertisementDTO> result = template.exchange(
                "/fleamarket/advertisement?word=mÁSODIK",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdvertisementDTO>>() {
                }).getBody();

        assertThat(result)
                .hasSize(3)
                .extracting(AdvertisementDTO::getLumberCategory)
                .contains(LumberCategory.BÚTOR, LumberCategory.FESTMÉNY, LumberCategory.MŰSZAKICIKK);
    }

    @Test
    void getByWordAndCategory() {
        List<AdvertisementDTO> result = template.exchange(
                "/fleamarket/advertisement?category=festmény&word=másOdiK",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdvertisementDTO>>() {
                }).getBody();

        assertThat(result)
                .hasSize(1)
                .extracting(AdvertisementDTO::getText)
                .contains("Második festmény.");
    }

    @Test
    void getByTextNotExists() {
        List<AdvertisementDTO> result = template.exchange(
                "/fleamarket/advertisement?word=nope",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdvertisementDTO>>() {
                }).getBody();

        assertThat(result).hasSize(0);
    }

    @Test
    void createInvalidText() {
        Problem result = template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(LumberCategory.FESTMÉNY, ""),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createInvalidCategory() {
        Problem result = template.postForObject("/fleamarket/advertisement",
                new CreateAdvertisementCommand(null, "Ipsum loren"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void updateValidText() {
        template.put("/fleamarket/advertisement/1",
                new UpdateAdvertisementCommand("modified"),
                AdvertisementDTO.class);

        AdvertisementDTO result =
                template.getForObject(
                        "/fleamarket/advertisement/1",
                        AdvertisementDTO.class);

        assertEquals("modified", result.getText());
    }

    @Test
    void updateInvalidText() {
        template.put("/fleamarket/advertisement/1",
                new UpdateAdvertisementCommand(""),
                AdvertisementDTO.class);

        AdvertisementDTO result =
                template.getForObject(
                        "/fleamarket/advertisement/1",
                        AdvertisementDTO.class);

        assertEquals("Első festmény.", result.getText());
    }

    @Test
    void deleteById() {
        template.delete("/fleamarket/advertisement/2");

        Problem result =
                template.getForObject(
                        "/fleamarket/advertisement/2",
                        Problem.class);

        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void deleteByCategory() {
        template.delete("/fleamarket/advertisement?category=BÚTOR");

        List<AdvertisementDTO> result = template.exchange(
                "/fleamarket/advertisement",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdvertisementDTO>>() {
                }).getBody();

        assertThat(result)
                .hasSize(6)
                .extracting(AdvertisementDTO::getText)
                .contains("Második bútor.", "Harmadik bútor.");
    }

    @Test
    void deleteByAllCategory() {
        template.delete("/fleamarket/advertisement");

        List<AdvertisementDTO> result = template.exchange(
                "/fleamarket/advertisement",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdvertisementDTO>>() {
                }).getBody();

        assertThat(result)
                .hasSize(4)
                .extracting(AdvertisementDTO::getText)
                .contains("Második bútor.", "Harmadik bútor.", "Második festmény.", "Harmadik festmény.");
    }
}