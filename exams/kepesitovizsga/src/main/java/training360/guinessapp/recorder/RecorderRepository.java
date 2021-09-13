package training360.guinessapp.recorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecorderRepository extends JpaRepository<Recorder, Long> {
}