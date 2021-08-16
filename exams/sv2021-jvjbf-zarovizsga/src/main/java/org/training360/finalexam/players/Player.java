package org.training360.finalexam.players;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.training360.finalexam.teams.Team;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "players")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name")
    private String name;

    private LocalDate birth;

    @Enumerated
    private PositionType position;

    @ManyToOne
    private Team team;

    public Player(String name, LocalDate birth, PositionType position) {
        this.name = name;
        this.birth = birth;
        this.position = position;
    }
}