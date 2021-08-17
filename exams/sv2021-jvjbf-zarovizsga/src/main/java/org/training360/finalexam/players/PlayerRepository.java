package org.training360.finalexam.players;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT COUNT(p) FROM Player p WHERE p.team.id=:id and p.position=:position")
    int playerOnThisPosition(long id, PositionType position);
}