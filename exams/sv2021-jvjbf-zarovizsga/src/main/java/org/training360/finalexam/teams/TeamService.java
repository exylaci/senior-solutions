package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.training360.finalexam.exceptions.NotFoundException;
import org.training360.finalexam.players.*;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    public List<TeamDTO> getTeams() {
        List<Team> teams = teamRepository.findAll();
        Type teamsDTOListType = new TypeToken<List<TeamDTO>>() {
        }.getType();
        return modelMapper.map(teams, teamsDTOListType);
    }

    public TeamDTO createTeam(CreateTeamCommand command) {
        Team team = new Team(command.getName());
        teamRepository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO addNewPlayer(long id, CreatePlayerCommand command) {
        Team team = findTeam(id);

        if (playerRepository.playerOnThisPosition(id, command.getPosition()) < 2) {
            Player player = new Player(command.getName(), command.getBirthDate(), command.getPosition());
            player.setTeam(team);
            playerRepository.save(player);
        }

        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public void signPlayer(long id, UpdateWithExistingPlayerCommand command) {
        Player player = playerRepository.getById(command.getPlayerId());

        if (player.getTeam() == null &&
                playerRepository.playerOnThisPosition(id, player.getPosition()) < 2) {
            Team team = findTeam(id);
            player.setTeam(team);
        }
    }

    private Team findTeam(long id) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("teams/not-found", "There is no team with this id: " + id));
    }
}