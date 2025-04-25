package com.example.demo.controller;

import com.example.demo.service.CallLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CallLogController.class)
@WithMockUser(username = "${demo.login.user.name}", password = "${demo.login.user.password}", roles = "${demo.login.user.roles}")
class CallLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CallLogService callLogService;

    private final String BASE_URL = "/api/heatmap/answer-rate";

    @Test
    void testValidRequestShouldPass() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("dateInput", "2024-04-25")
                        .param("numberOfShades", "5")
                        .param("startHour", "10")
                        .param("endHour", "15"))
                .andExpect(status().isOk());
    }

    @Test
    void testInvalidDateFormat() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("dateInput", "25-04-2024")
                        .param("numberOfShades", "5"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Date must be in format YYYY-MM-DD")));
    }

    @Test
    void testNumberOfShadesTooLow() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("dateInput", "2024-04-25")
                        .param("numberOfShades", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("numberOfShades must be at least 3")));
    }

    @Test
    void testNumberOfShadesTooHigh() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("dateInput", "2024-04-25")
                        .param("numberOfShades", "11"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("numberOfShades must be at most 10")));
    }

    @Test
    void testStartHourOutOfBounds() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("dateInput", "2024-04-25")
                        .param("numberOfShades", "5")
                        .param("startHour", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("startHour must be between 0 and 23")));
    }

    @Test
    void testEndHourBeforeStartHour() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("dateInput", "2024-04-25")
                        .param("numberOfShades", "5")
                        .param("startHour", "15")
                        .param("endHour", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("endHour must be greater than or equal to startHour")));
    }

    @Test
    void testMissingDateParam() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("numberOfShades", "5"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("dateInput: must not be null")));
    }

    @Test
    void testMissingShadesParam() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("dateInput", "2025-05-24"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("numberOfShades: must not be null")));
    }
}
