package com.example.muruna.serviciousuarios.infrastructure.adapters.output.services;

import com.example.muruna.serviciousuarios.application.ports.output.UserRepositoryService;
import com.example.muruna.serviciousuarios.domain.model.PhoneDto;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities.Phone;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities.User;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers(){

        List<User> userList = userRepository.findAllByDeleteIsFalse();

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

        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
        user.setPassword(encryptedPassword);
        Date actualDate = new Date();
        user.setCreated(actualDate);
        user.setActive(true);
        user.setLastLogin(actualDate);
        user.setModified(actualDate);

        user = userRepository.save(user);

        return objectMapper.convertValue(user, UserDto.class);
    }

    @Override
    public boolean existsUserById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        ObjectMapper objectMapper = new ObjectMapper();

        Optional<User> userOptional = userRepository.findById(userDto.getId());

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Date actualDate = new Date();
            user.setActive(userDto.getActive() == null || userDto.getActive());
            user.setModified(actualDate);
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            if(userDto.getPhones()!= null) {
                user.setPhones(new ArrayList<>());
                for (PhoneDto phoneDto : userDto.getPhones()) {
                    user.getPhones().add(new ObjectMapper().convertValue(phoneDto, Phone.class));
                }
            }

            user = userRepository.save(user);
            return objectMapper.convertValue(user, UserDto.class);
        }
        return null;

    }

    @Override
    public void deleteUser(UUID id) {

        userRepository.findById(id).ifPresent(user -> {
            user.setDelete(true);
            userRepository.save(user);
        });

    }
}
