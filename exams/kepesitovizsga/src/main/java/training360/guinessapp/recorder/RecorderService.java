package training360.guinessapp.recorder;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import training360.guinessapp.dto.RecorderCreateCommand;
import training360.guinessapp.dto.RecorderDto;
import training360.guinessapp.dto.RecorderShortDto;

import java.util.List;

@Service
@AllArgsConstructor
public class RecorderService {
    private final RecorderRepository repository;
    private final ModelMapper modelMapper;

    public RecorderDto createRecorder(RecorderCreateCommand command) {
        Recorder recorder = new Recorder();
        recorder.setName(command.getName());
        recorder.setDateOfBirth(command.getDateOfBirth());
        repository.save(recorder);
        return modelMapper.map(recorder, RecorderDto.class);
    }

    public List<RecorderShortDto> getRecorders() {
        return modelMapper.map(repository.getRecordersFiltered(), new TypeToken<List<RecorderShortDto>>() {
        }.getType());
    }
}