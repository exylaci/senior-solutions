package fightergame.fighter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    private Integer win;
    private Integer draw;
    private Integer lose;
}