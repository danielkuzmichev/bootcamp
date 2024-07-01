package com.example.bootcamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {

    @Schema(example = "Smith")
    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;

    @Schema(example = "John")
    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;

    @Schema(example = "john-smith@mail.com")
    @Email
    @JsonProperty("email")
    private String email;

    @Schema(example = "Java Developer")
    @NotEmpty
    @JsonProperty("role")
    private String role;

}
