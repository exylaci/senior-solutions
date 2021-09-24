package mentortools.student;

import lombok.AllArgsConstructor;
import mentortools.trainingclass.CreateUpdateTrainingClassCommand;
import mentortools.trainingclass.TrainingClass;
import mentortools.trainingclass.TrainingClassDto;
import mentortools.trainingclass.TrainingClassRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentRepository repository;
    private ModelMapper modelMapper;

    public List<StudentDto> getStudents() {
        Type targetListType = new TypeToken<List<StudentDto>>() {
        }.getType();
        List<Student> result = repository.findAll();
        return modelMapper.map(result, targetListType);
    }

    public StudentDto getStudent(long id) {
        Student student = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no student with this id: " + id));
        return modelMapper.map(student, StudentDto.class);
    }

    public StudentDto createStudent(CreateUpdateStudentCommand command) {
        Student student = new Student(
                command.getName(),
                command.getEmail(),
                command.getGitHubUserName(),
                command.getComment());
        repository.save(student);
        return modelMapper.map(student, StudentDto.class);
    }

    @Transactional
    public StudentDto updateStudent(long id, CreateUpdateStudentCommand command) {
        Student student = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no student with this id: " + id));

        student.setName(command.getName());
        student.setEmail(command.getEmail());
        student.setGitHubUserName(command.getGitHubUserName());
        student.setComment(command.getComment());

        return modelMapper.map(student, StudentDto.class);
    }

    public void deleteStudent(long id) {
        repository.deleteById(id);
    }
}