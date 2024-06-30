package com.example.bootcamp.controller;

import com.example.bootcamp.dto.CandidateDTO;
import com.example.bootcamp.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/candidate")
@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated CandidateDTO candidate){
        candidateService.register(candidate);
        return new ResponseEntity("Candidate is signed-up and his status increased", HttpStatus.OK);
    }

}
