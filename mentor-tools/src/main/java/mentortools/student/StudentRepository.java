package mentortools.student;

import mentortools.trainingclass.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
