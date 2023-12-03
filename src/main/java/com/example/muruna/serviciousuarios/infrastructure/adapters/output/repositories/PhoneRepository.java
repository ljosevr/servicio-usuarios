package com.example.muruna.serviciousuarios.infrastructure.adapters.output.repositories;

import com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {
}
