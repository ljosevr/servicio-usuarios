package com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private String password;

    private Date created;

    private Date modified;

    private Date lastLogin;

    private String token;

    private boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Phone> phones;
}
