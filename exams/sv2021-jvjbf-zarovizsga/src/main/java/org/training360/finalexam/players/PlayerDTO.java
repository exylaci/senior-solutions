package org.training360.finalexam.players;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.training360.finalexam.teams.TeamDTO;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private PositionType position;

    @JsonBackReference
    private TeamDTO team;
}