package fightergame.contest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContestDto {
    private Long id;
    private String fighterNameA;
    private String fighterNameB;
    private Result result;
}