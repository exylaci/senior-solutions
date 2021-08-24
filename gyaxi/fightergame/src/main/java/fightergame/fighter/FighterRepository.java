package fightergame.fighter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FighterRepository extends JpaRepository<Fighter, Long> {
    @Query("SELECT f FROM Fighter f where f.name=:name")
    Fighter findFighter(String name);

//    @Query("SELECT f, (f.score.win*3+f.score.draw) as s FROM Fighter f order by s DESC LIMIT 3")
//    List<Object[]> getTournamentPodium();
}