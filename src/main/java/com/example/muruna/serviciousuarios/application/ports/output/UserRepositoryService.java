package com.example.muruna.serviciousuarios.application.ports.output;

import com.example.muruna.serviciousuarios.domain.model.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryService {

    List<UserDto> getAllUsers();

    boolean emailInUse(String email);

    UserDto createUser(UserDto newUser);

    boolean existsUserById(UUID id);

    UserDto updateUser(UserDto userDto);

    void deleteUser(UUID id);
}
