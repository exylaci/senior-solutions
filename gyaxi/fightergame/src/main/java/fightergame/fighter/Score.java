package fightergame.fighter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private Integer win = 0;
    private Integer draw = 0;
    private Integer lose = 0;
}