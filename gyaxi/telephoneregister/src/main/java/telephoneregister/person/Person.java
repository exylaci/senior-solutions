package telephoneregister.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import telephoneregister.exceptions.MissingDataException;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    public static final String EMAIL_PATTERN = "[a-z0-9]+[a-z0-9\\.-]*@([a-z0-9-]+\\.)+[a-z]{2,4}";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name")
    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "person_id"))
    @MapKeyColumn(name = "phone_number_type")
    @Column(name = "phone_number")
    private Map<String, String> phones;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "addresses", joinColumns = @JoinColumn(name = "person_id"))
    @MapKeyColumn(name = "address_type")
    @Column(name = "address")
    private Map<String, String> addresses;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "email")
    private Set<String> emails;

    private String comment;

    protected Person(String name) {
        this.name = name;
    }

    protected void addPhone(AddPhoneCommand command) {
        if (command.getPhoneNumberType().isBlank() || command.getPhoneNumber().isBlank()) {
            return;
        }
        if (phones == null) {
            phones = new HashMap<>();
        }
        phones.put(command.getPhoneNumberType(), command.getPhoneNumber());
    }

    protected void addAddress(AddAddressCommand command) {
        if (command.getAddressType().isBlank() || command.getAddress().isBlank()) {
            return;
        }
        if (addresses == null) {
            addresses = new HashMap<>();
        }
        addresses.put(command.getAddressType(), command.getAddress());
    }

    protected void addEmail(AddEmailCommand command) {
        if (command.getEmail().isBlank()) {
            return;
        }
        if (emails == null) {
            emails = new HashSet<>();
        }
        if (command.getEmail().matches(EMAIL_PATTERN)) {
            emails.add(command.getEmail());
        } else {
            throw new MissingDataException(
                    "/api/persons",
                    "Add person",
                    "Invalid e-mail address: " + command.getEmail());
        }
    }

    protected void isValid() {
        if (phones != null && !phones.isEmpty()) {
            return;
        }
        if (addresses != null && !addresses.isEmpty()) {
            return;
        }
        if (emails != null && !emails.isEmpty()) {
            return;
        }
        throw new MissingDataException(
                "/api/persons",
                "Add new person",
                "At least one information has to be given to a person !");
    }
}