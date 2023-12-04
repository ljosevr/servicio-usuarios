package com.example.muruna.serviciousuarios.infrastructure.adapters.input.controllers;

import com.example.muruna.serviciousuarios.application.ports.input.UserService;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.ErrorResponse;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.UsuarioException;
import com.example.muruna.serviciousuarios.infrastructure.adapters.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name ="Registrar Usuarios", description = "API para registrar Usuarios")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    public static final String NO_AUTORIZADO = "No Autorizado";

    private final UserService userService;

    @GetMapping("/")
    @Operation(summary = "Get All Users", description = "Retrieve a list of all users")
    @ApiResponse(responseCode = "200", description = "Successful Operation",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)))
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping(value = "/create",consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create User", description = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User Created Successfully")
    public ResponseEntity<?> createUser(
            @Parameter(description = "User data to create a new user", required = true, schema = @Schema(implementation = UserDto.class))
            @Valid @RequestBody UserDto newUser, @RequestHeader("Authorization") String authHeader) throws UsuarioException {
        String token = extractToken(authHeader);
        if(JwtUtils.isJwtValid(token)) {
            newUser.setToken(token);
            UserDto userCreatedDto = userService.createuser(newUser);
            return new ResponseEntity<>(userCreatedDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponse(NO_AUTORIZADO), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Update an exists user")
    @ApiResponse(responseCode = "202", description = "User Update Successfully")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto
            , @RequestHeader("Authorization") String authHeader) throws UsuarioException {
        String token = extractToken(authHeader);
        if(JwtUtils.isJwtValid(token)) {
            userDto.setToken(token);
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } else {
            return new ResponseEntity<>(new ErrorResponse(NO_AUTORIZADO), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Delete an exists user")
    @ApiResponse(responseCode = "204", description = "User Delete Successfully")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id, @RequestHeader("Authorization") String authHeader) throws UsuarioException {
        String token = extractToken(authHeader);
        if(JwtUtils.isJwtValid(token)) {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return new ResponseEntity<>(new ErrorResponse(NO_AUTORIZADO), HttpStatus.UNAUTHORIZED);
        }
    }



    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @PostConstruct
    private void createToken(){
        log.info("Token: "+JwtUtils.createJwtToken());
    }

}
