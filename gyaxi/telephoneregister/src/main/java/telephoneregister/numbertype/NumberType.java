package telephoneregister.numbertype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "number_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeName;

    public NumberType(String typeName) {
        this.typeName = typeName;
    }
}