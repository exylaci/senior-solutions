package org.training360.finalexam.players;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;
    private final ModelMapper modelMapper;

    public PlayerDTO createPlayer(CreatePlayerCommand command) {
        Player player = new Player(command.getName(), command.getBirthDate(), command.getPosition());
        repository.save(player);
        return modelMapper.map(player, PlayerDTO.class);
    }

    public List<PlayerDTO> getPlayers() {
        List<Player> players = repository.findAll();
        Type playersDTOListType = new TypeToken<List<PlayerDTO>>() {
        }.getType();
        return modelMapper.map(players, playersDTOListType);
    }

    public void deletePlayer(long id) {
        repository.deleteById(id);
    }
}