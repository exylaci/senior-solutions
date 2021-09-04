package bank.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyAndCreateAccountCommand {
    @NotBlank(message = "Name cannot be blank!")
    private String name;
}