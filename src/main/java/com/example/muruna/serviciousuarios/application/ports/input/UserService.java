package com.example.muruna.serviciousuarios.application.ports.input;

import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.UsuarioException;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto createuser(UserDto newUser) throws UsuarioException;
}
