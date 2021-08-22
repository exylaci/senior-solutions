package therapy.participant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import therapy.exceptions.NotFoundException;
import therapy.session.Session;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "participants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "participant_name")
    private String name;

    @ManyToMany
    @JoinTable(name = "sessions_participants",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    @EqualsAndHashCode.Exclude
    private Set<Session> sessions;

    public Participant(String name) {
        this.name = name;
    }

    public void addSession(Session session) {
        if (sessions == null) {
            sessions = new HashSet<>();
        }
        sessions.add(session);
    }

    public void removeSession(Session session) {
        if (sessions == null || sessions.isEmpty()) {
            throw new NotFoundException("/api/sessions", "remove a participant", "Session list is empty!");
        }
        sessions.remove(session);
    }
}