package training360.guinnessapp.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeatWorldRecordCommand {
    private Long recorderId;
    private Double value;
}