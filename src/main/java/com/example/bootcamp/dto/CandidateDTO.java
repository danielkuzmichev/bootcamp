package com.example.bootcamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {

    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;

    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;

    @Email
    @JsonProperty("email")
    private String email;

    @NotEmpty
    @JsonProperty("role")
    private String role;

}
