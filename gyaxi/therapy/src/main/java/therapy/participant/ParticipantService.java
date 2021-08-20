package therapy.participant;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import therapy.exceptions.InvalidDataException;
import therapy.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class ParticipantService {
    private final ParticipantRepository repository;
    private final ModelMapper modelMapper;

    public List<ParticipantDto> getParticipants() {
        List<Participant> participants = repository.findAll();
        Type participantsDTOListType = new TypeToken<List<ParticipantDto>>() {
        }.getType();
        return modelMapper.map(participants, participantsDTOListType);
    }

    public ParticipantWithSessionDto getParticipant(long id) {
        Participant participant = findParticipant(id);
        return modelMapper.map(participant, ParticipantWithSessionDto.class);
    }

    public ParticipantDto createParticipant(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidDataException("/api/participants", "Create participant", "Invalid participant name: " + name);
        }
        Participant participant = new Participant(name);
        repository.save(participant);
        return modelMapper.map(participant, ParticipantDto.class);
    }

    @Transactional
    public ParticipantDto updateParticipant(long id, UpdateParticipantCommand command) {
        Participant participant = findParticipant(id);
        participant.setName(command.getName());
        return modelMapper.map(participant, ParticipantDto.class);
    }

    public void deleteParticipant(long id) {
        repository.deleteById(id);
    }

    public Participant findParticipant(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("/api/participants", "Find participant", "There is no participant with this id: " + id));
    }
}