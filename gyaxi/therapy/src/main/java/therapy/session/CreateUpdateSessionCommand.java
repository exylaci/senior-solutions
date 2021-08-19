package therapy.session;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import therapy.validations.IsValidName;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateSessionCommand {
    @IsValidName(message = "The name is too short.", minLength = 5)
    @Schema(example = "Dr. No", description = "The name of the therapist.")
    private String therapist;

    @Schema(example = "Somewhere", description = "The place of the session.")
    private String location;

    @Future(message = "Session can be organised only to the future!")
    @Schema(example = "2097-10-25T13:45:00", description = "The start of the session.")
    private LocalDateTime startsAt;
}