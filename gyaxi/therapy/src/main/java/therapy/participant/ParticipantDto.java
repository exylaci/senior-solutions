package therapy.participant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import therapy.session.SessionDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private Long id;
    private String name;

    @JsonBackReference
    private SessionDto session;
}