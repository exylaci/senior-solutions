package therapy.participant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import therapy.session.Session;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    public Participant(String name) {
        this.name = name;
    }
}