package fightergame.fighter;

import fightergame.contest.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "fighters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fighter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fighter_name")
    private String name;

    private Integer vitality;
    private Integer damage;

    @Embedded
    private Score score = new Score();

    public Fighter(String name, Integer vitality, Integer damage) {
        this.name = name;
        this.vitality = vitality;
        this.damage = damage;
    }

    public void increaseScore(Result result) {
        score.increaseScore(result);
    }
}