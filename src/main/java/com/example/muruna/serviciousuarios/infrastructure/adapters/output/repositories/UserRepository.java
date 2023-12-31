package com.example.muruna.serviciousuarios.infrastructure.adapters.output.repositories;

import com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByDeleteIsFalse();

    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
