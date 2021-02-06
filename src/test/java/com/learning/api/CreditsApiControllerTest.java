package com.learning.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CreditsApiController.class)
public class CreditsApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("Test Add Credit Card Detail")
    @Test
    public void testAddCredits() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/ccp/credits")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Correlation-Id","5645282")
                .content(request()))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Test Get All Credit Cards Detail")
    @Test
    public void testGetCredits() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/ccp/credits")
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Correlation-Id","5645282"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String request() {
        return "{\"name\": \"John\",\"cardNumber\": 654428635281,\"limit\": 100000}";
    }
}
