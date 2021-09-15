package training360.guinessapp.worldrecord;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import training360.guinessapp.dto.WorldRecordCreateCommand;
import training360.guinessapp.dto.WorldRecordDto;
import training360.guinessapp.exception.NotFoundException;
import training360.guinessapp.recorder.Recorder;
import training360.guinessapp.recorder.RecorderRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class
WorldRecordService {
    private final WorldRecordRepository repository;
    private final RecorderRepository recorderRepository;
    private final ModelMapper modelMapper;

    public WorldRecordDto createWorldRecord(WorldRecordCreateCommand command) {
        Optional<Recorder> recorder = recorderRepository.findById(command.getRecorderId());

        if (recorder.isEmpty()) {
            throw new NotFoundException("/api/worldrecords", "Recorder not found", "Recorder not found with this id: " + command.getRecorderId());
        }

        WorldRecord worldRecord = new WorldRecord();
        worldRecord.setDescription(command.getDescription());
        worldRecord.setValue(command.getValue());
        worldRecord.setUnitOfMeasure(command.getUnitOfMeasure());
        worldRecord.setDateOfRecord(command.getDateOfRecord());
        worldRecord.setRecorder(recorder.get());
        repository.save(worldRecord);
        return modelMapper.map(worldRecord, WorldRecordDto.class);
    }
}