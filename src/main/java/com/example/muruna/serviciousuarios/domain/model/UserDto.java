package com.example.muruna.serviciousuarios.domain.model;

import com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions.ValidPasswordPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserDto {
    private UUID id;

    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @ValidPasswordPattern
    private String password;

    private Date created;

    private Date modified;

    private Date lastLogin;

    private String token;

    private boolean active;

    private List<PhoneDto> phones;
}
