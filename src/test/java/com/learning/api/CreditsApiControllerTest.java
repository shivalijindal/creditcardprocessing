package com.learning.api;

import com.learning.model.CreditCard;
import com.learning.model.CreditCardDetails;
import com.learning.service.CardProcessingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CreditsApiController.class)
public class CreditsApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CardProcessingService cardProcessingService;

    @DisplayName("Test Add Credit Card Detail")
    @Test
    public void testAddCredits() throws Exception{
        //Mock addCredits service call
        cardProcessingService.addCredits(Mockito.any(CreditCardDetails.class));

        mvc.perform(MockMvcRequestBuilders.post("/ccp/credits")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Correlation-Id","5645282")
                .content(request()))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Test Get All Credit Cards Detail")
    @Test
    public void testGetCredits() throws Exception{
        //Create Mock data
        CreditCardDetails creditCard = new CreditCardDetails();
        creditCard.name("Tim")
                .cardNumber(new BigInteger("73342639365"))
                .limit(BigDecimal.valueOf(300000))
                .balance(BigDecimal.valueOf(100));

        //Mock getAllCredits service call
        Mockito.when(cardProcessingService.getAllCreditCardDetails()).thenReturn(List.of(creditCard));

        String details = mvc.perform(MockMvcRequestBuilders.get("/ccp/credits")
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Correlation-Id","5645282"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println("Output: " + details);
    }

    private String request() {
        return "{\"name\": \"John\",\"cardNumber\": 654428635281,\"limit\": 100000}";
    }
}
