package therapy.session;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddParticipantCommand {
    @Schema(example = "1", description = "The id of the participant adding to a session.")
    private Long id;
}