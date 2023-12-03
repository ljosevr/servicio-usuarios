package com.example.muruna.serviciousuarios.infrastructure.adapters.output.services;

import com.example.muruna.serviciousuarios.application.ports.output.UserRepositoryService;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities.User;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    public List<UserDto> getAllUsers(){

        List<User> userList = userRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();

        List<UserDto> userDtos = new ArrayList<>();

        userList.forEach(user ->
            userDtos.add(objectMapper.convertValue(user, UserDto.class)));
        return userDtos;
    }

    @Override
    public boolean emailInUse(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto createUser(UserDto newUser) {

        ObjectMapper objectMapper = new ObjectMapper();

        User user = objectMapper.convertValue(newUser, User.class);
        Date actualDate = new Date();
        user.setCreated(actualDate);
        user.setActive(true);
        user.setLastLogin(actualDate);
        user.setModified(actualDate);

        user = userRepository.save(user);

        return objectMapper.convertValue(user, UserDto.class);
    }
}
