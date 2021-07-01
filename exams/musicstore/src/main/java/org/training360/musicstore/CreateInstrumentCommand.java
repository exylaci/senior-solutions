package org.training360.musicstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstrumentCommand {
    @NotBlank
    private String brand;

    @NotNull
    private InstrumentType instrumentType;

    @PositiveOrZero
    private int price;
}
