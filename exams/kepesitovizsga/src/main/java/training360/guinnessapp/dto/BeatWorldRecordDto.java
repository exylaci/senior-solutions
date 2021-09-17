package training360.guinnessapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeatWorldRecordDto {
    private String description;
    private String unitOfMeasure;
    private String oldRecorderName;
    private String newRecorderName;
    private Double newRecordValue;
    private Double recordDifference;
}