package telephoneregister.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonCommand {
    @NotBlank
    private String name;
    private AddPhoneCommand addPhoneCommand;
    private AddAddressCommand addAddressCommand;
    private AddEmailCommand addEmailCommand;
    private String comment;
}