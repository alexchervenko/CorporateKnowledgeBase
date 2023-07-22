package ru.chervenko.EnsetKB.utils.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chervenko.EnsetKB.dto.PersonDTO;
import ru.chervenko.EnsetKB.service.PersonService;

@Component
public class PersonDTOValidator implements Validator {
    private final PersonService personService;

    public PersonDTOValidator(PersonService personService) {
        this.personService = personService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return PersonDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonDTO personDTO = (PersonDTO) target;

        if (personService.findByUsername(personDTO.getUsername()).isPresent()){
            errors.rejectValue("username", "", "Ошибка логина");
        }
    }
}
