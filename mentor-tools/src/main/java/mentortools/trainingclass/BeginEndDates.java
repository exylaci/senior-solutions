package mentortools.trainingclass;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeginEndDates {
    @NotNull
    @Schema(example = "2021-06-01", description = "First date of the training class.")
    private LocalDate begin;

    @NotNull
    @Schema(example = "2021-08-22", description = "Last date of the training class. (Must be later than the first date.)")
    private LocalDate end;
}