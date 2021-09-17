package training360.guinnessapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldRecordCreateCommand {
    @NotBlank(message = "must not be blank")
    private String description;

    @NotNull(message = "must not be null")
    private Double value;

    @NotBlank(message = "must not be blank")
    private String unitOfMeasure;

    @NotNull(message = "must not be null")
    private LocalDate dateOfRecord;

    @NotNull(message = "must not be null")
    private Long recorderId;
}