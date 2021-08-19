package therapy.session;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import therapy.exceptions.NotFoundException;
import therapy.participant.Participant;
import therapy.participant.ParticipantService;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository repository;
    private final ModelMapper modelMapper;
    private final ParticipantService service;

    public List<SessionDto> getSessions() {
        List<Session> sessions = repository.findAll();
        Type sessionsDTOListType = new TypeToken<List<SessionDto>>() {
        }.getType();
        return modelMapper.map(sessions, sessionsDTOListType);
    }

    public SessionDto getSession(long id) {
        Session session = findSession(id);
        return modelMapper.map(session, SessionDto.class);
    }

    public SessionDto createSession(CreateUpdateSessionCommand command) {
        Session session = new Session(command.getTherapist(), command.getLocation(), command.getStartsAt());
        repository.save(session);
        return modelMapper.map(session, SessionDto.class);
    }

    @Transactional
    public SessionDto updateSession(long id, CreateUpdateSessionCommand command) {
        Session session = findSession(id);
        session.setTherapist(command.getTherapist());
        session.setLocation(command.getLocation());
        session.setStartsAt(command.getStartsAt());
        return modelMapper.map(session, SessionDto.class);
    }

    @Transactional
    public SessionDto addParticipant(long id, AddParticipantCommand command) {
        Session session = findSession(id);
        Participant participant = service.findParticipant(command.getId());
        session.addParticipant(participant);
        return modelMapper.map(session, SessionDto.class);
    }

    @Transactional
    public SessionDto removeParticipant(long sessionId, long participantId) {
        Session session = findSession(sessionId);
        Participant participant = service.findParticipant(participantId);
        session.removeParticipant(participant);
        return modelMapper.map(session, SessionDto.class);
    }

    public void deleteSession(long id) {
        Session session = findSession(id);
        session.getParticipants().forEach(session::removeParticipant);
        repository.deleteById(id);
    }

    private Session findSession(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("/api/sessions", "Find session", "There is no session with this id: " + id));
    }
}