package fightergame.fighter;

import fightergame.exceptions.BadRequest;
import fightergame.exceptions.NotFound;
import org.modelmapper.TypeToken;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class FighterService {
    private final FighterRepository repository;
    private final ModelMapper modelMapper;

    public List<FighterDto> getFighters() {
        List<Fighter> fighters = repository.findAll();
        Type playersDTOListType = new TypeToken<List<FighterDto>>() {
        }.getType();
        return modelMapper.map(fighters, playersDTOListType);
    }

    public FighterDto getFighter(long id) {
        Fighter fighter = findFighter(id);
        return modelMapper.map(fighter, FighterDto.class);
    }

    public FighterDto createFighter(CreateUpdateFighterCommand command) {
        if (repository.findFighter(command.getName()) != null) {
            throw new BadRequest("/api/fighters/duplicate", "Create fighter", "A fighter with this name already exists!");
        }
        Fighter fighter = new Fighter(command.getName(), command.getVitality(), command.getDamage());
        repository.save(fighter);
        return modelMapper.map(fighter, FighterDto.class);
    }

    @Transactional
    public FighterDto updateFighter(long id, CreateUpdateFighterCommand command) {
        Fighter temp = repository.findFighter(command.getName());
        if (temp != null || temp.getId() != id) {
            throw new BadRequest("/api/fighters/duplicate", "Create fighter", "A fighter with this name already exists!");
        }
        Fighter fighter = findFighter(id);
        fighter.setName(command.getName());
        fighter.setVitality(command.getVitality());
        fighter.setDamage(command.getDamage());
        return modelMapper.map(fighter, FighterDto.class);
    }

    public void deleteFighter(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFound("/url/fighters/not-found", "Delete fighter", "There is no fighter with this id: " + id);
        }
    }

    public Fighter findFighter(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFound("/url/fighters/not-found", "Find fighter", "There is no fighter with this id: " + id));
    }
}