package fleamarket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdvertisementCommand {
    @NotBlank(message = "Kötelező hírdetés szöveget írni!")
    private String text;
}