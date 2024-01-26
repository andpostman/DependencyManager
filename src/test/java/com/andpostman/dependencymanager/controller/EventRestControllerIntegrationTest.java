package com.andpostman.dependencymanager.controller;

import com.andpostman.dependencymanager.dto.EventDto;
import com.andpostman.dependencymanager.service.DependencyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DependencyController.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DisplayName("Операции контроллера с events")
class EventRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DependencyServiceImpl dependencyService;

    @Test
    @DisplayName("Проверка когда событие пустое")
    void whenEventIsEmpty_thenReturns200() throws Exception{
        EventDto event = new EventDto("B","v1.2",true);
        mockMvc.perform(post("/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Проверка когда поле в запросе null")
    void whenNullValue_thenReturns500() throws Exception {
        mockMvc.perform(post("/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"project\":null,\"version\":\"1.2\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(
                        containsString("project is marked non-null")
                ));
    }
}
