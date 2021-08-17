package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PlayerService playerService;

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

        if (hasThisKindVacancyPosition(id, command.getPosition())) {
            Player player = new Player(command.getName(), command.getBirthDate(), command.getPosition());
            player.setTeam(team);
            team.addPlayer(player);
        }

        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public void signPlayer(long id, UpdateWithExistingPlayerCommand command) {
        Player player = playerService.findPlayer(command.getPlayerId());

        if (player.hasNoTeam() &&
                hasThisKindVacancyPosition(id, player.getPosition())) {
            Team team = findTeam(id);
            player.setTeam(team);
            team.addPlayer(player);
        }
    }

    private Team findTeam(long id) {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("teams/not-found", "There is no team with this id: " + id));
    }

    private boolean hasThisKindVacancyPosition(long teamId, PositionType position) {
        return playerRepository.playerOnThisPosition(teamId, position) < 2;
    }
}