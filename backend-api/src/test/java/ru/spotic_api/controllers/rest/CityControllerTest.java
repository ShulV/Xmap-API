package ru.spotic_api.controllers.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spotic_api.models.City;
import ru.spotic_api.services.CityService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("City controller")
@WebMvcTest(CityController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @Test
    @DisplayName("GET /api/city?name=Москва должен возвращать 200 OK и город")
    void shouldReturnCityByName() throws Exception {
        when(cityService.getByName("Москва"))
                .thenReturn(new City(1L, "Москва"));

        mockMvc.perform(get("/api/city").param("name", "Москва"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Москва"));

        verify(cityService).getByName("Москва");
    }
}