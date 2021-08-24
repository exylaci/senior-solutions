package fightergame.contest;

import fightergame.fighter.Fighter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "contests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fighter_name_a")
    private String fighterNameA;
    @Column(name = "fighter_name_b")
    private String fighterNameB;

    @Enumerated(EnumType.STRING)
    private Result result;

    @Transient
    private Fighter fighterA;

    @Transient
    private Fighter fighterB;

    public Contest(Fighter fighterA, Fighter fighterB) {
        this.fighterA = fighterA;
        this.fighterB = fighterB;
        fighterNameA = fighterA.getName();
        fighterNameB = fighterB.getName();
    }

    public void doFight() {
        countResult();
        setScores();
    }

    private void countResult() {
        if (fighterA.getVitality() / fighterB.getDamage() > fighterB.getVitality() / fighterA.getDamage()) {
            result = Result.WIN_A;
        } else if (fighterA.getVitality() / fighterB.getDamage() < fighterB.getVitality() / fighterA.getDamage()) {
            result = Result.WIN_B;
        } else {
            result = Result.DRAW;
        }
    }

    private void setScores() {
        fighterA.increaseScore(result);
        switch (result) {
            case WIN_A -> fighterB.increaseScore(Result.WIN_B);
            case DRAW -> fighterB.increaseScore(Result.DRAW);
            case WIN_B -> fighterB.increaseScore(Result.WIN_A);
        }
    }
}
