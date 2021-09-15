package training360.guinessapp.recorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import training360.guinessapp.worldrecord.WorldRecord;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recorder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recorder_name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "recorder", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private Set<WorldRecord> worldRecords;
}