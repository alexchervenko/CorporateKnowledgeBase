package ru.chervenko.EnsetKB.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.chervenko.EnsetKB.model.SecurityUser;
import ru.chervenko.EnsetKB.repository.PersonRepository;

@Service
public class PersonDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public PersonDetailsService(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователя с таким именем не существует"));
    }
}
