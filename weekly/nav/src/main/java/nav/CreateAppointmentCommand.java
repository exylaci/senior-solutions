package nav;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentCommand {
    @IsValidTaxId(message = "The TAX ID is invalid!")
    private String taxId;

    @IsValidTime(message = "Start time must be in the future!")
    private Interval interval;

    @IsValidCode(message = "This code does not exists!")
    private String caseCode;
}