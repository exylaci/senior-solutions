package telephoneregister.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPhoneCommand {
    @NotBlank
    private String phoneNumberTypeName;
    @NotBlank
    private String accessType;
    @NotBlank
    private String phoneNumber;
}