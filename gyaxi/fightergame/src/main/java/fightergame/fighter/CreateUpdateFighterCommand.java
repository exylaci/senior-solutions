package fightergame.fighter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateFighterCommand {
    @NotBlank(message = "Name cannot be blank!")
    @Schema(example = "Alien", description = "The name of the fighter.")
    private String name;

    @Min(value = 1)
    private Integer vitality;

    @Min(value = 1)
    private Integer damage;
}