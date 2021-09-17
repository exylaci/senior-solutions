package training360.guinnessapp.recorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecorderRepository extends JpaRepository<Recorder, Long> {
    @Query("SELECT r FROM Recorder r WHERE (r.name LIKE 'B%') OR (r.name LIKE '%e%') ORDER BY r.name DESC")
    List<Recorder> getRecordersFiltered();
}