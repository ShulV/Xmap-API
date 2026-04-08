package ru.spotic_api.controllers.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("LocaleController")
@WebMvcTest(controllers = LocaleController.class)
@AutoConfigureMockMvc(addFilters = false)
class LocaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /locale должен возвращать 200 OK")
    void shouldReturnOk() throws Exception {
        System.out.println("LocaleControllerTest.shouldReturnOk executed");
        mockMvc.perform(get("/locale"))
                .andExpect(status().isOk());
    }
}