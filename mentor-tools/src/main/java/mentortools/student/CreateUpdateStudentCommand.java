package mentortools.student;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.trainingclass.BeginEndDates;
import mentortools.trainingclass.IsValidDates;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateStudentCommand {
    @NotNull
    @NotBlank
    @Schema(example = "John Doe", description = "Name of the student.")
    private String name;

    @NotNull
    @NotBlank
    @Schema(example = "john.doe@mailserver.hu", description = "Email address of the student.")
    private String email;

    @NotNull
    @NotBlank
    @Schema(example = "johndoe", description = "GitHub user name of the student.")
    private String gitHubUserName;

    @Schema(example = "It's a comment about him.", description = "Comment related to the student.")
    private String comment;
}