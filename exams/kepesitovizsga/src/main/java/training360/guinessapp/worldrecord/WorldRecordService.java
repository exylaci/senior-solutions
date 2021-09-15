package training360.guinessapp.worldrecord;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import training360.guinessapp.dto.BeatWorldRecordCommand;
import training360.guinessapp.dto.BeatWorldRecordDto;
import training360.guinessapp.dto.WorldRecordCreateCommand;
import training360.guinessapp.dto.WorldRecordDto;
import training360.guinessapp.exception.BadRequestException;
import training360.guinessapp.exception.NotFoundException;
import training360.guinessapp.recorder.Recorder;
import training360.guinessapp.recorder.RecorderRepository;

import javax.transaction.Transactional;
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

    @Transactional
    public BeatWorldRecordDto beatWorldRecord(Long worldRecordId, BeatWorldRecordCommand command) {
        Optional<WorldRecord> worldRecordOptional = repository.findById(worldRecordId);
        if (worldRecordOptional.isEmpty()) {
            throw new NotFoundException("/api/worldrecords", "World record not found", "World record not found with this id: " + worldRecordId);
        }

        Optional<Recorder> recorder = recorderRepository.findById(command.getRecorderId());
        if (recorder.isEmpty()) {
            throw new NotFoundException("/api/worldrecords", "Recorder not found", "Recorder not found with this id: " + command.getRecorderId());
        }

        if (command.getValue() < worldRecordOptional.get().getValue()) {
            throw new BadRequestException("/api/worldrecords", "Can not beat", "The " + " is less than " + command.getRecorderId());
        }

        WorldRecord worldRecord = worldRecordOptional.get();

        BeatWorldRecordDto beatWorldRecordDto = new BeatWorldRecordDto();
        beatWorldRecordDto.setDescription(worldRecord.getDescription());
        beatWorldRecordDto.setUnitOfMeasure(worldRecord.getUnitOfMeasure());
        beatWorldRecordDto.setOldRecorderName(worldRecord.getRecorder().getName());
        beatWorldRecordDto.setNewRecorderName(recorder.get().getName());
        beatWorldRecordDto.setNewRecordValue(command.getValue());
        beatWorldRecordDto.setRecordDifference(command.getValue() - worldRecord.getValue());

        worldRecord.setRecorder(recorder.get());
        worldRecord.setValue(command.getValue());

        return beatWorldRecordDto;
    }
}