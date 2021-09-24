package mentortools.trainingclass;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateTrainingClassCommand {
    @NotNull
    @NotBlank
    @Schema(example = "Junior Vállalati Java Backend", description = "Subject of the training class.")
    private String title;

    @IsValidDates
    private BeginEndDates beginEndDates;
}