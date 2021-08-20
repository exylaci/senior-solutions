package therapy.participant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import therapy.session.SessionDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantWithSessionDto {
    private Long id;
    private String name;
    private SessionDto session;
}