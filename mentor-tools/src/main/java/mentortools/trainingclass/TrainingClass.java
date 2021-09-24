package mentortools.trainingclass;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "training_classes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Operations on training class.")
public class TrainingClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull
    @Schema(example = "Junior Vállalati Java Backend", description = "Subject of the training class.")
    private String title;

    @Column(name = "begin_date")
    @NotNull
    @Schema(example = "2021.6.1", description = "Beginning of the training class.")
    private LocalDate begin;

    @Column(name = "end_date")
    @Schema(example = "2021.8.9", description = "End of the training class.")
    private LocalDate end;

    public TrainingClass(String title, LocalDate begin, LocalDate end) {
        this.title = title;
        this.begin = begin;
        this.end = end;
    }
}