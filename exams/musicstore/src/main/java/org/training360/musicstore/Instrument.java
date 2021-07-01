package org.training360.musicstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instrument {
    private long id;
    private String brand;
    private InstrumentType type;
    private int price;
    private LocalDate postDate;

    public Instrument(long id, CreateInstrumentCommand command) {
        this.id = id;
        this.brand = command.getBrand();
        this.type = command.getInstrumentType();
        this.price = command.getPrice();
        this.postDate = LocalDate.now();
    }

    public void setPostDate() {
        this.postDate = LocalDate.now();
    }
}
