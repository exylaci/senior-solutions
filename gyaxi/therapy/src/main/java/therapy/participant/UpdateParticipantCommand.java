package therapy.participant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParticipantCommand {
    @NotBlank(message = "Name cannot be blank!")
    @Schema(example = "Mr. No Name", description = "The name of the participant.")
    private String name;
}