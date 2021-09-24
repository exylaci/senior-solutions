package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingClassService {
    private TrainingClassRepository repository;
    private ModelMapper modelMapper;

    public List<TrainingClassDto> getTrainingClasses() {
        Type targetListType = new TypeToken<List<TrainingClassDto>>() {
        }.getType();
        List<TrainingClass> result = repository.findAll();
        return modelMapper.map(result, targetListType);
    }

    public TrainingClassDto getTrainingClass(long id) {
        TrainingClass result = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no training class with this id: " + id));
        return modelMapper.map(result, TrainingClassDto.class);
    }

    public TrainingClassDto createTrainingClass(CreateUpdateTrainingClassCommand command) {
        TrainingClass trainingClass = new TrainingClass(
                command.getTitle(),
                command.getBeginEndDates().getBegin(),
                command.getBeginEndDates().getEnd());
        repository.save(trainingClass);
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    @Transactional
    public TrainingClassDto updateTrainingClass(long id, CreateUpdateTrainingClassCommand command) {
        TrainingClass trainingClass = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no training class with this id: " + id));

        trainingClass.setTitle(command.getTitle());
        trainingClass.setBegin(command.getBeginEndDates().getBegin());
        trainingClass.setEnd(command.getBeginEndDates().getEnd());

        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    public void deleteTrainingClass(long id) {
        repository.deleteById(id);
    }
}