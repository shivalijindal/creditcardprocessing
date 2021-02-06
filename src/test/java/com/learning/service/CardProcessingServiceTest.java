package com.learning.service;

import com.learning.model.CreditCardDetails;
import com.learning.service.impl.CardProcessingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CardProcessingServiceTest {

    CardProcessingService cardProcessingService;

    @BeforeEach
    public void setup() {
        cardProcessingService = new CardProcessingServiceImpl();
    }

    @Test
    public void testAddCredits() {
        //Create Input data
        CreditCardDetails cardDetails = new CreditCardDetails();
        cardDetails.name("Hillary")
                .cardNumber(9383645284640L)
                .limit(Double.valueOf(30000))
                .balance(Double.valueOf(100));

        Assertions.assertNotNull(cardProcessingService);
        cardProcessingService.addCredits(cardDetails);
        Assertions.assertTrue(true);
    }

    @Test
    public void testGetAllCredits() {
        Assertions.assertNotNull(cardProcessingService);
        List<CreditCardDetails> creditCardDetailsList = cardProcessingService.getAllCreditCardDetails();
        assertEquals(1, creditCardDetailsList.size(), "Size of the list does not match");
        Assertions.assertAll(() -> {
            assertEquals("Tim", creditCardDetailsList.get(0).getName(), "Name on Card does not match");
            assertEquals(73342639365L, creditCardDetailsList.get(0).getCardNumber(), "Card Number does not match");
            assertEquals(Double.valueOf(300000), creditCardDetailsList.get(0).getLimit(), "Card Limit does not match");
            assertEquals(Double.valueOf(100), creditCardDetailsList.get(0).getBalance(), "Card Balance doe not match");
        });
    }
}
