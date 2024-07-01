package com.example.bootcamp.api;

import com.example.bootcamp.controller.CandidateController;
import com.example.bootcamp.dto.CandidateDTO;
import com.example.bootcamp.exception.RemoteServiceException;
import com.example.bootcamp.integration.T1Client;
import com.example.bootcamp.service.CandidateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.timeout.ReadTimeoutException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CandidateController.class)
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private T1Client t1Client;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private CandidateService candidateService;

    /**
     * Проверка ответов в зависмости от переданной роли
     * */
    @ParameterizedTest
    @MethodSource("provideRegisterCandidateWithRoleTestCases")
    void testRegisterCandidateWithRole(String role, int expectedStatus) throws Exception {
        Mockito.when(t1Client.getRoles()).thenReturn(List.of("Разработчик Java"));

        when(t1Client.signUp(any(CandidateDTO.class))).thenReturn("Success");
        when(t1Client.getCode(anyString())).thenReturn("123456");
        when(t1Client.setStatus(anyString(), anyString())).thenReturn("Status Updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/candidate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSONCandidateWithRole(role)))
            .andExpect(status().is(expectedStatus));
    }

    private String getJSONCandidateWithRole(String role) throws JsonProcessingException {
        CandidateDTO candidateDTO = new CandidateDTO("KUZ", "DAN", "test@example.com", role);
        String candidateJson = objectMapper.writeValueAsString(candidateDTO);

        return candidateJson;
    }

    private static Stream<Arguments> provideRegisterCandidateWithRoleTestCases() {
        return Stream.of(
                Arguments.of("Разработчик Java", 200),
                Arguments.of("Таролог", 400)
        );
    }

    /**
     * Проверка ответа при GATEWAY_TIMEOUT от запрашиваемого сервиса
     * */
    @Test
    void testRegisterCandidateWithDelay() throws Exception {
        when(t1Client.getRoles()).thenThrow(new ReadTimeoutException());
        mockMvc.perform(MockMvcRequestBuilders.post("/candidate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSONCandidateWithRole("Тестировщик")))
            .andExpect(status().is(504));
    }

    /**
     * Проверка ответа при любых ошибках от запрашиваемого сервиса
     * */
    @Test
    void testRegisterCandidateWithRemoteServiceError() throws Exception {
        when(t1Client.getRoles()).thenThrow(new RemoteServiceException());
        mockMvc.perform(MockMvcRequestBuilders.post("/candidate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJSONCandidateWithRole("Тестировщик")))
            .andExpect(status().is(502));
    }

}
