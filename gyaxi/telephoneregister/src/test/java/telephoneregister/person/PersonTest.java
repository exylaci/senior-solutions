package telephoneregister.person;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import telephoneregister.number.AccessType;
import telephoneregister.numbertype.NumberType;
import telephoneregister.numbertype.NumberTypeDto;
import telephoneregister.numbertype.NumberTypeService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from phones", "delete from addresses", "delete from emails", "delete from persons", "delete from number_types"})
public class PersonTest {
    @Autowired
    TestRestTemplate template;

    @Autowired
    NumberTypeService service;

    NumberType numberType;

    @BeforeEach
    void init() {
        Long id = template.postForObject(
                "/api/number-types?numbertype=mobile",
                null,
                NumberTypeDto.class).getId();
        numberType = service.findNumberType(id);
    }

    @Test
    void testAddNewPerson() {
        PersonDto result = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("mobile", "PRIVATE", "+1234"),
                        new AddAddressCommand("HOME", "chez moi"),
                        new AddEmailCommand("a@b.cd"),
                        "no comment"),
                PersonDto.class);
        assertEquals("John Doe", result.getName());
        assertEquals(numberType, result.getPhones());
        assertEquals("+1234", result.getPhones().get(numberType));
        assertEquals("chez moi", result.getAddresses().get(AddressType.HOME));
        assertEquals("a@b.cd", result.getEmails());
        assertEquals("no comment", result.getComment());
    }

    @Test
    void testAddNewPersonWithOnlyPhone() {
        PersonDto result = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("mobile", "PRIVATE", "+1234"),
                        new AddAddressCommand("", ""),
                        new AddEmailCommand(""),
                        ""),
                PersonDto.class);
        assertEquals("John Doe", result.getName());
        assertEquals("+1234", result.getPhones().get("mobile"));
    }

    @Test
    void testAddNewPersonWithOnlyAddress() {
        PersonDto result = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("", "", ""),
                        new AddAddressCommand("HOME", "chez moi"),
                        new AddEmailCommand(""),
                        ""),
                PersonDto.class);
        assertEquals("John Doe", result.getName());
        assertEquals("chez moi", result.getAddresses().get(AddressType.HOME));
    }

    @Test
    void testAddNewPersonWithOnlyEmail() {
        PersonDto result = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("", "", ""),
                        new AddAddressCommand("", ""),
                        new AddEmailCommand("a@b.cd"),
                        ""),
                PersonDto.class);
        assertEquals("John Doe", result.getName());
        assertEquals("a@b.cd", result.getEmails().get(0));
    }

    @Test
    void testAddNewPersonWithoutData() {
        Problem result = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("", "", ""),
                        new AddAddressCommand("", ""),
                        new AddEmailCommand(""),
                        "no comment"),
                Problem.class);
        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void testGetPerson() {
        Long id = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("mobile", "PRIVATE", "+1234"),
                        new AddAddressCommand("HOME", "chez moi"),
                        new AddEmailCommand("a@b.cd"),
                        "no comment"),
                PersonDto.class).getId();

        PersonDto result = template.exchange(
                "/api/persons/" + id,
                HttpMethod.GET,
                null,
                PersonDto.class).getBody();

        assertEquals("John Doe", result.getName());
        assertEquals("+1234", result.getPhones().get("mobile"));
        assertEquals("chez moi", result.getAddresses().get(AddressType.HOME));
        assertEquals("a@b.cd", result.getEmails().get(0));
        assertEquals("no comment", result.getComment());
    }

    @Test
    void testGetPersons() {
        template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("mobile", "PRIVATE", "+1234"),
                        new AddAddressCommand("HOME", "chez moi"),
                        new AddEmailCommand("a@b.cd"),
                        "no comment"),
                PersonDto.class).getId();
        template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "Jane Doe",
                        new AddPhoneCommand("mobile", "PRIVATE", "+1234"),
                        new AddAddressCommand("HOME", "chez moi"),
                        new AddEmailCommand("a@b.cd"),
                        "no comment"),
                PersonDto.class).getId();

        List<String> result = template.exchange(
                "/api/persons/names",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {
                }).getBody();

        assertThat(result).containsExactly("Jane Doe", "John Doe");
    }

    @Test
    void testAddPhone() {
        Long id = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("", "", ""),
                        new AddAddressCommand("", ""),
                        new AddEmailCommand("a@b.cd"),
                        ""),
                PersonDto.class).getId();
        template.put(
                "/api/persons/" + id + "/phones",
                new AddPhoneCommand("mobile", "PRIVATE", "+1234"));
        template.put(
                "/api/persons/" + id + "/phones",
                new AddPhoneCommand("mobile", "COMPANY", "+4321"));
        template.put(
                "/api/persons/" + id + "/phones",
                new AddPhoneCommand("fix line", "PRIVATE", "0"));

        PersonDto result = template.exchange(
                "/api/persons/" + id,
                HttpMethod.GET,
                null,
                PersonDto.class).getBody();
        assertEquals("John Doe", result.getName());
        assertEquals("+4321", result.getPhones().get("mobile"));
        assertEquals("0", result.getPhones().get("fix line"));
    }

    @Test
    void testAddAddress() {
        Long id = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("", "", ""),
                        new AddAddressCommand("", ""),
                        new AddEmailCommand("a@b.cd"),
                        ""),
                PersonDto.class).getId();
        template.put(
                "/api/persons/" + id + "/addresses",
                new AddAddressCommand("HOME", "chez moi"));
        template.put(
                "/api/persons/" + id + "/addresses",
                new AddAddressCommand("HOME", "over there"));
        template.put(
                "/api/persons/" + id + "/addresses",
                new AddAddressCommand("WORKPLACE", "office building"));

        PersonDto result = template.exchange(
                "/api/persons/" + id,
                HttpMethod.GET,
                null,
                PersonDto.class).getBody();
        assertEquals("John Doe", result.getName());
        assertEquals("over there", result.getAddresses().get(AddressType.HOME));
        assertEquals("office building", result.getAddresses().get(AddressType.WORKPLACE));
    }

    @Test
    void testAddEmail() {
        Long id = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("fix line", "PRIVATE", "0"),
                        new AddAddressCommand("", ""),
                        new AddEmailCommand(""),
                        ""),
                PersonDto.class).getId();
        template.put(
                "/api/persons/" + id + "/emails",
                new AddEmailCommand("name@mailserver.com"));
        template.put(
                "/api/persons/" + id + "/emails",
                new AddEmailCommand("name@mailserver.com"));
        template.put(
                "/api/persons/" + id + "/emails",
                new AddEmailCommand("a@b.cd"));

        PersonDto result = template.exchange(
                "/api/persons/" + id,
                HttpMethod.GET,
                null,
                PersonDto.class).getBody();
        assertEquals("John Doe", result.getName());
        assertThat(result.getEmails()).containsExactlyInAnyOrder("name@mailserver.com", "a@b.cd");
    }

    @Test
    void testAddInvalidEmail() {
        Long id = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("fix line", "PRIVATE", "0"),
                        new AddAddressCommand("", ""),
                        new AddEmailCommand(""),
                        ""),
                PersonDto.class).getId();
        Problem problem = template.exchange(
                "/api/persons/" + id + "/emails",
                HttpMethod.PUT,
                new HttpEntity<>(new AddEmailCommand("invalid@emailaddress")),
                Problem.class).getBody();

        PersonDto result = template.exchange(
                "/api/persons/" + id,
                HttpMethod.GET,
                null,
                PersonDto.class).getBody();
        assertEquals("John Doe", result.getName());
        assertEquals(0, result.getEmails().size());
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }

    @Test
    void testCreateWithInvalidEmail() {
        Problem problem = template.postForObject(
                "/api/persons",
                new CreatePersonCommand(
                        "John Doe",
                        new AddPhoneCommand("", "", ""),
                        new AddAddressCommand("", ""),
                        new AddEmailCommand("invalid@emailaddress"),
                        ""),
                Problem.class);

        List<String> result = template.exchange(
                "/api/persons/names",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {
                }).getBody();
        assertEquals(0, result.size());
        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }
}