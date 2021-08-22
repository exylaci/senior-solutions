package therapy.participant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import therapy.session.SessionDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantWithSessionDto {
    private Long id;
    private String name;
    private List<SessionDto> sessions;
}