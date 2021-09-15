package training360.guinessapp.worldrecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import training360.guinessapp.recorder.Recorder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "world_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorldRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description_of_worldrecord")
    private String description;

    @Column(name = "value")
    private Double value;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "date_of_record")
    private LocalDate dateOfRecord;

    @ManyToOne
    @JoinColumn(name = "recorder_id")
    private Recorder recorder;
}