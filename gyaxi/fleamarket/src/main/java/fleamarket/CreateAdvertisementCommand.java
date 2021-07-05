package fleamarket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdvertisementCommand {
    @NotNull(message = "Kötelező kategóriát magadni!")
    private LumberCategory lumberCategory;

    @NotBlank(message = "Kötelező hírdetés szöveget írni!")
    private String text;
}