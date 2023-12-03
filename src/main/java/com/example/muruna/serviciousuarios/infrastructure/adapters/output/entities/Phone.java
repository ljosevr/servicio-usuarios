package com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String number;

    @JsonProperty("citycode")
    private String cityCode;
    @JsonProperty("contrycode")
    private String countryCode;

}
