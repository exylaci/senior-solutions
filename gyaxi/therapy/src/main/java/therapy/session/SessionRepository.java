package therapy.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT s FROM Session s LEFT JOIN FETCH s.participants WHERE s.id=:id")
    Session findSessionWithParticipants(long id);
}