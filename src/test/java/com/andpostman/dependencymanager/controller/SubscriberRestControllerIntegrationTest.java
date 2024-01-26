package com.andpostman.dependencymanager.controller;

import com.andpostman.dependencymanager.dto.SubscriberDto;
import com.andpostman.dependencymanager.service.DependencyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DependencyController.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DisplayName("Операции контроллера с subscribers")
class SubscriberRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DependencyServiceImpl dependencyService;

    @Test
    @DisplayName("Проверка когда подписчика не существует")
    void whenSubscriberDoesNotExists_thenReturns200() throws Exception{
        SubscriberDto subscriber = new SubscriberDto("E","B","v1.2");
        mockMvc.perform(post("/checkAndSubscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriber)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("false")
                ));
    }

    @Test
    @DisplayName("Проверка когда задано какое-либо поле null")
    void whenNullValue_thenReturns500() throws Exception {
        mockMvc.perform(post("/checkAndSubscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"project\":\"E\",\"masterProject\":\"B\",\"masterVersion\":null}"))
                .andExpect(status().isInternalServerError());
    }
}
