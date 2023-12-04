package com.example.muruna.serviciousuarios.application.ports.input;

import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.UsuarioException;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDto> getAllUsers();


    UserDto createuser(UserDto newUser) throws UsuarioException;

    UserDto updateUser(UUID id, UserDto userDto) throws UsuarioException;

    void deleteUser(UUID id) throws UsuarioException;
}
