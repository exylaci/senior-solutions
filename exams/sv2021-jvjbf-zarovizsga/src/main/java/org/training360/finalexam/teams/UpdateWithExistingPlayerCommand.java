package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWithExistingPlayerCommand {
    private Long playerId;
}