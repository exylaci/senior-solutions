package telephoneregister.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private Map<String, String> phones;
    private Map<AddressType, String> addresses;
    private List<String> emails;
    private String comment;
}