package com.example.bootcamp.service;

import com.example.bootcamp.CandidateStatus;
import com.example.bootcamp.dto.CandidateDTO;
import com.example.bootcamp.exception.RoleMismatchException;
import com.example.bootcamp.integration.T1Client;
import com.example.bootcamp.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private T1Client t1Client;

    public void register(CandidateDTO candidate) {
        if (existRole(candidate.getRole())) {
            t1Client.signUp(candidate);
            String email = candidate.getEmail();
            String code = t1Client.getCode(email);
            String token = Converter.getEncodedEmailAndCode(email,code);
            t1Client.setStatus(token, CandidateStatus.INCREASED.name());
        } else {
            throw new RoleMismatchException();
        }
    }

    public boolean existRole(String role) {
        List <String> roles = t1Client.getRoles();
        return roles.contains(role);
    }

}
