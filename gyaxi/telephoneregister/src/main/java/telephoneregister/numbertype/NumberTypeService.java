package telephoneregister.numbertype;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import telephoneregister.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class NumberTypeService {
    private final NumberTypeRepository repository;
    private final ModelMapper modelMapper;

    public NumberTypeDto createNumberType(String numberTypeName) {
        NumberType numberType = findNumberType(numberTypeName);
        if (numberType == null) {
            numberType = new NumberType(numberTypeName);
            repository.save(numberType);
        }
        return modelMapper.map(numberType, NumberTypeDto.class);
    }

    public List<NumberTypeDto> getNumberTypes() {
        return modelMapper.map(repository.findAll(), new TypeToken<List<NumberTypeDto>>() {
        }.getType());
    }

    @Transactional
    public NumberTypeDto updateNumberType(long id, String numberTypeName) {
        NumberType numberType = findNumberType(id);
        numberType.setTypeName(numberTypeName);
        return modelMapper.map(numberType, NumberTypeDto.class);
    }

    public NumberTypeDto deleteNumberType(long id) {
        NumberType numberType = findNumberType(id);
        repository.deleteById(id);
        return modelMapper.map(numberType, NumberTypeDto.class);
    }

    public NumberType findNumberType(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("/api/number-types", "find number type", "There is no phone number type with this id: " + id));
    }

    public NumberType findNumberType(String phoneNumberTypeName) {
        return repository.find(phoneNumberTypeName);
    }
}