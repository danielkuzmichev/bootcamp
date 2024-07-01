package com.example.bootcamp.integration;

import com.example.bootcamp.dto.CodeDTO;
import com.example.bootcamp.dto.RolesDTO;
import com.example.bootcamp.dto.StatusDTO;
import com.example.bootcamp.dto.CandidateDTO;
import com.example.bootcamp.exception.RemoteServiceException;
import io.netty.handler.timeout.ReadTimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import java.time.Duration;
import java.util.List;

@Component
public class T1Client {

    private final WebClient webClient;
    private static final int TIMEOUT = 100000;

    public T1Client(@Value("${t1client.base-api-url}")String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .clientConnector(new ReactorClientHttpConnector(
                HttpClient.create().followRedirect(true)
                    .responseTimeout(Duration.ofMillis(TIMEOUT))
            ))
            .build();
    }

    public String signUp(CandidateDTO candidate) {
        return webClient.post()
            .uri("/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(candidate), CandidateDTO.class)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> {
                    switch (clientResponse.statusCode().value()) {
                        case 504: throw new ReadTimeoutException();
                        default: throw new RemoteServiceException();
                    }
                }
            )
            .bodyToMono(String.class)
            .block();
    }

    public List<String> getRoles() {
        RolesDTO rolesDTO = webClient.get()
            .uri("/get-roles")
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> {
                    throw new RemoteServiceException();
                }
            )
            .bodyToMono(RolesDTO.class)
            .block();

        return rolesDTO.getRoles();
    }

    public String getCode(String email) {
        String url = UriComponentsBuilder.fromUriString("/get-code")
            .queryParam("email", email)
            .toUriString();
        CodeDTO codeDTO = webClient.get()
            .uri(url)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> {
                    throw new RemoteServiceException();
                }
            )
            .bodyToMono(CodeDTO.class)
            .block();

        return codeDTO.getCode();
    }

    public String setStatus(String token, String status) {
        StatusDTO statusDTO = new StatusDTO(token, status);

        return webClient.post()
            .uri("/set-status")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(statusDTO), StatusDTO.class)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> {
                    throw new RemoteServiceException();
                }
            )
            .bodyToMono(String.class)
            .block();
    }
}
