package com.example.bootcamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatusDTO {

    @JsonProperty("token")
    private String token;

    @JsonProperty("status")
    private String status;
}