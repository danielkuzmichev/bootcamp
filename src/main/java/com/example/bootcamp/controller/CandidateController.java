package com.example.bootcamp.controller;

import com.example.bootcamp.dto.CandidateDTO;
import com.example.bootcamp.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Candidate")
@RequestMapping("/candidate")
@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Register candidate", description = "Registers a new candidate, increased his status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Candidate registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Candidate to be registered",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CandidateDTO.class))
            )
            @RequestBody @Validated CandidateDTO candidate
    ){
        candidateService.register(candidate);
        return new ResponseEntity("Candidate is signed-up and his status increased", HttpStatus.OK);
    }

}
