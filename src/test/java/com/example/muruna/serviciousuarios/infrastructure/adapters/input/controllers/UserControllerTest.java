package com.example.muruna.serviciousuarios.infrastructure.adapters.input.controllers;

import com.example.muruna.serviciousuarios.application.ports.input.UserService;
import com.example.muruna.serviciousuarios.domain.model.PhoneDto;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.UsuarioException;
import com.example.muruna.serviciousuarios.infrastructure.adapters.utils.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;
    private String validToken;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("password123");

        PhoneDto phone1 = new PhoneDto();
        phone1.setNumber("123456789");
        phone1.setCityCode("1");
        phone1.setCountryCode("57");

        PhoneDto phone2 = new PhoneDto();
        phone2.setNumber("987654321");
        phone2.setCityCode("2");
        phone2.setCountryCode("58");

        List<PhoneDto> phones = Arrays.asList(phone1, phone2);

        userDto.setPhones(phones);

        Assertions.assertNotNull(userDto.toString());

        validToken = "Bearer " + JwtUtils.createJwtToken();
    }

    @Test
    void getAllUsersTest() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(userDto));

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void createUserTest_ValidToken() throws UsuarioException {
        when(userService.createuser(any(UserDto.class))).thenReturn(userDto);

        ResponseEntity<?> response = userController.createUser(userDto, validToken);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).createuser(any(UserDto.class));
    }

    @Test
    void createUserTest_InvalidToken() throws UsuarioException {
        ResponseEntity<?> response = userController.createUser(userDto, "Bearer invalid_token");

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(userService, never()).createuser(any(UserDto.class));
    }

}
