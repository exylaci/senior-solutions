package fightergame.contest;

import fightergame.exceptions.BadRequest;
import fightergame.exceptions.NotFound;
import fightergame.fighter.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ContestService {
    private final ContestRepository contestRepository;
    private final FighterRepository fighterRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ContestDto startContest(Long idA, Long idB) {
        if (idA.equals(idB)) {
            throw new BadRequest("/api/contests", "Duel", "Cannot fight with themself!");
        }
        Fighter fighterA = fighterRepository.findById(idA).orElseThrow(() -> new NotFound("/url/fighters/not-found", "Find fighter", "There is no fighter with this id: " + idA));
        Fighter fighterB = fighterRepository.findById(idB).orElseThrow(() -> new NotFound("/url/fighters/not-found", "Find fighter", "There is no fighter with this id: " + idB));
        Contest contest = new Contest(fighterA, fighterB);
        contest.doFight();
        contestRepository.save(contest);
        return modelMapper.map(contest, ContestDto.class);
    }

    public List<ContestDto> getContests() {
        List<Contest> contests = contestRepository.findAll();
        Type playersDTOListType = new TypeToken<List<ContestDto>>() {
        }.getType();
        return modelMapper.map(contests, playersDTOListType);
    }

    public ContestDto getContest(long id) {
        Contest contest = contestRepository
                .findById(id)
                .orElseThrow(() -> new NotFound("/url/contests/not-found", "Find contest", "There is no contest with this id: " + id));
        return modelMapper.map(contest, ContestDto.class);
    }

    public List<FighterDto> getTournamentPodium() {
        return fighterRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparingInt((Fighter a) -> (a.getScore().getWin() * 3 + a.getScore().getDraw()))
                        .reversed())
                .limit(3)
                .map(fighter -> modelMapper.map(fighter, FighterDto.class))
                .toList();
    }
}