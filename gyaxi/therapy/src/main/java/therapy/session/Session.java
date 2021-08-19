package therapy.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import therapy.exceptions.NotFoundException;
import therapy.participant.Participant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String therapist;
    private String location;
    private LocalDateTime startsAt;

    @OneToMany(mappedBy = "session", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private Set<Participant> participants;

    public Session(String therapist, String location, LocalDateTime startsAt) {
        this.therapist = therapist;
        this.location = location;
        this.startsAt = startsAt;
    }

    public void addParticipant(Participant participant) {
        if (participants == null) {
            participants = new HashSet<>();
        }
        participants.add(participant);
        participant.setSession(this);
    }

    public void removeParticipant(Participant participant) {
        if (participants == null || participants.isEmpty()) {
            throw new NotFoundException("/api/sessions", "remove a participant", "Participant list is empty!");
        }
        participants.remove(participant);
        participant.setSession(null);
    }
}