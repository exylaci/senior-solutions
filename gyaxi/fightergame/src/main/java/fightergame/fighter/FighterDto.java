package fightergame.fighter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FighterDto {
    private Long id;
    private String name;
    private Integer vitality;
    private Integer damage;
    private Score score;
}