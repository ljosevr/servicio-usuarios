package com.example.muruna.serviciousuarios.domain.services;

import com.example.muruna.serviciousuarios.application.ports.input.UserService;
import com.example.muruna.serviciousuarios.application.ports.output.UserRepositoryService;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.UsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public UserDto updateUser(UUID id, UserDto userDto) throws UsuarioException {

        if(!id.equals(userDto.getId())){
            throw new UsuarioException("Id en Objeto a Actualizar no Coincide con Id del Endpoint");
        }

        if(!userRepositoryService.existsUserById(userDto.getId())){
            throw new UsuarioException("No existe Usuario con id: " + userDto.getId());
        }

        UserDto userDtoUpdated = userRepositoryService.updateUser(userDto);
        if(userDtoUpdated == null) {
            throw new UsuarioException("Error al Actualizar Usuario con id: " + userDto.getId());
        }

        return userDtoUpdated;
    }

    @Override
    public void deleteUser(UUID id) throws UsuarioException {
        if(!userRepositoryService.existsUserById(id)){
            throw new UsuarioException("No existe Usuario con Id: " + id);
        }

        userRepositoryService.deleteUser(id);

    }
}
