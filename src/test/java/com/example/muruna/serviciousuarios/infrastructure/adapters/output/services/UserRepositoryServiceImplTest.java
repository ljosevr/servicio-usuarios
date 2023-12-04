package com.example.muruna.serviciousuarios.infrastructure.adapters.output.services;

import com.example.muruna.serviciousuarios.domain.model.PhoneDto;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.config.SecurityConfig;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities.User;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRepositoryServiceImpl userRepositoryService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserDto userDto;
    private User userEntity;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

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

        userEntity = objectMapper.convertValue(userDto, User.class);

        Assertions.assertNotNull(userEntity.toString());
    }

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntity));

        List<UserDto> result = userRepositoryService.getAllUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void emailInUseTest_Exists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        boolean result = userRepositoryService.emailInUse("test@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    void emailInUseTest_NotExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        boolean result = userRepositoryService.emailInUse("test@example.com");

        assertFalse(result);
        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    void createUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(userEntity);
        UserDto createdUser = userRepositoryService.createUser(userDto);

        assertNotNull(createdUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

}
