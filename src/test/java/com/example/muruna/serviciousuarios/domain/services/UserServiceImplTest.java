package com.example.muruna.serviciousuarios.domain.services;

import com.example.muruna.serviciousuarios.application.ports.output.UserRepositoryService;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.UsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepositoryService userRepositoryService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("password123");
    }

    @Test
    void getAllUsersTest() {
        when(userRepositoryService.getAllUsers()).thenReturn(Arrays.asList(userDto));

        List<UserDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepositoryService, times(1)).getAllUsers();
    }

    @Test
    void createUser_Successful() throws UsuarioException {
        when(userRepositoryService.emailInUse(anyString())).thenReturn(false);
        when(userRepositoryService.createUser(any(UserDto.class))).thenReturn(userDto);

        UserDto result = userService.createuser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());
        verify(userRepositoryService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    void createUser_EmailInUse() {
        when(userRepositoryService.emailInUse(anyString())).thenReturn(true);

        assertThrows(UsuarioException.class, () -> userService.createuser(userDto));
        verify(userRepositoryService, never()).createUser(any(UserDto.class));
    }

}
