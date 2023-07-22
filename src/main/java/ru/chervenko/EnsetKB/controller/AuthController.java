package ru.chervenko.EnsetKB.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chervenko.EnsetKB.dto.PersonDTO;
import ru.chervenko.EnsetKB.model.Person;
import ru.chervenko.EnsetKB.service.PersonService;
import ru.chervenko.EnsetKB.utils.validator.PersonDTOValidator;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonService personService;
    private final PersonDTOValidator personDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(PersonService personService,
                          PersonDTOValidator personDTOValidator,
                          ModelMapper modelMapper) {
        this.personService = personService;
        this.personDTOValidator = personDTOValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String loginPage(){
        if (isAuthenticated()) {
            return "redirect:/problems";
        }
        return "auth/login";
    }


    @GetMapping("/register")
    public String register(@ModelAttribute("user") Person person) {
        return "auth/register";
    }


    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid PersonDTO personDTO,
                                  BindingResult bindingResult) {
        personDTOValidator.validate(personDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        personService.createUser(convertToPerson(personDTO));
        return "redirect:/auth/login";
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

}
