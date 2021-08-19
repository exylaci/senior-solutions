package therapy.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import therapy.participant.ParticipantDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private Long id;
    private String therapist;
    private String location;
    private LocalDateTime startsAt;
    private List<ParticipantDto> participants;
}