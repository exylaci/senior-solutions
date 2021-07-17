package nav;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NavService {
    private List<CaseType> caseTypes = Arrays.asList(
            new CaseType("001", "Adóbevallás"),
            new CaseType("002", "Befizetés"));

    private ModelMapper modelMapper;

    public NavService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<CaseTypeDTO> getCaseTypes() {
        Type targetListType = new TypeToken<List<CaseTypeDTO>>() {
        }.getType();
        return modelMapper.map(caseTypes, targetListType);
    }

    public AppointmentDTO createAppointment(CreateAppointmentCommand command) {
        Appointment appointment = new Appointment(
                command.getTaxId(), command.getInterval(), command.getCaseCode());
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    public boolean taxIdValidator(String taxId) {
        if (taxId.length() != 10) {
            return false;
        }

        int checkSum = 0;
        for (int i = 1; i < 10; ++i) {
            char c = taxId.charAt(i - 1);
            if (!Character.isDigit(c)) {
                return false;
            }
            checkSum += Integer.parseInt(Character.toString(c)) * i;
        }
        checkSum %= 11;

        System.out.println(checkSum);

        return Character.isDigit(taxId.charAt(9)) &&
                checkSum == Integer.parseInt(taxId.substring(9));
    }

    public boolean codeValidator(String code) {
        return caseTypes
                .stream()
                .map(CaseType::getCode)
                .anyMatch(cod -> cod.equals(code));
    }
}
