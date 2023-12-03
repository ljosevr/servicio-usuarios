package com.example.muruna.serviciousuarios.domain.services;

import com.example.muruna.serviciousuarios.application.ports.input.UserService;
import com.example.muruna.serviciousuarios.application.ports.output.UserRepositoryService;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.UsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepositoryService.getAllUsers();
    }

    @Override
    public UserDto createuser(UserDto newUser) throws UsuarioException {

        if(userRepositoryService.emailInUse(newUser.getEmail())){
            throw new UsuarioException("Email Ya fue Registrado");
        }

        newUser = userRepositoryService.createUser(newUser);

        return newUser;
    }
}
