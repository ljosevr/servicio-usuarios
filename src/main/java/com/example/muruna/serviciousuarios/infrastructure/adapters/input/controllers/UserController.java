package com.example.muruna.serviciousuarios.infrastructure.adapters.input.controllers;

import com.example.muruna.serviciousuarios.application.ports.input.UserService;
import com.example.muruna.serviciousuarios.domain.model.UserDto;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.ErrorResponse;
import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.UsuarioException;
import com.example.muruna.serviciousuarios.infrastructure.adapters.utils.JwtUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name ="Registrar Usuarios", description = "API para registrar Usuarios")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

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
            return new ResponseEntity<>(new ErrorResponse("No Autorizado"), HttpStatus.UNAUTHORIZED);
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
