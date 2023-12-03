package com.example.muruna.serviciousuarios.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class PhoneDto {
    @JsonIgnore
    private UUID id;
    private String number;
    @JsonProperty(value = "citycode")
    private String cityCode;
    @JsonProperty("contrycode")
    private String countryCode;

}
