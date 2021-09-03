package telephoneregister.person;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telephoneregister.exceptions.NotFoundException;
import telephoneregister.numbertype.NumberType;
import telephoneregister.numbertype.NumberTypeService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final NumberTypeService numberTypeService;
    private final ModelMapper modelMapper;

    public List<String> getAllPersonsNames() {
        return repository.getAllPersonsNames();
    }

    public PersonDto getPerson(long id) {
        Person result = repository.findById(id).get();
        return modelMapper.map(result, PersonDto.class);
    }

    @Transactional
    public PersonDto createPerson(CreatePersonCommand command) {
        NumberType numberType = numberTypeService.findNumberType(command.getAddPhoneCommand().getPhoneNumberTypeName());
//        NumberType numberType = service.findNumberType(command.getAddPhoneCommand().getPhoneNumberTypeId());
        Person person = new Person(command.getName());
        person.addPhone(numberType, command.getAddPhoneCommand());
        person.addAddress(command.getAddAddressCommand());
        person.addEmail(command.getAddEmailCommand());
        person.setComment(command.getComment());
        person.isValid();
        repository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto addPhone(long id, AddPhoneCommand command) {
        NumberType numberType = numberTypeService.findNumberType(command.getPhoneNumberTypeName());
        Person person = repository.findById(id).orElseThrow(() -> new NotFoundException(
                "/api/person", "Add phone number", "Cannot find person with this id: " + id));
        person.addPhone(numberType, command);
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto addAddress(long id, AddAddressCommand command) {
        Person person = repository.findById(id).orElseThrow(() -> new NotFoundException(
                "/api/person", "Add address", "Cannot find person with this id: " + id));
        person.addAddress(command);
        return modelMapper.map(person, PersonDto.class);
    }

    @Transactional
    public PersonDto addEmail(long id, AddEmailCommand command) {
        Person person = repository.findById(id).orElseThrow(() -> new NotFoundException(
                "/api/person", "Add email", "Cannot find person with this id: " + id));
        person.addEmail(command);
        return modelMapper.map(person, PersonDto.class);
    }
}