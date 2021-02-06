package com.learning.model;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CreditCardTest {

    @BeforeEach
    public void setup() {
        System.out.println("Before Each Test");
    }

    @Test
    public void testCreditCard() {
        CreditCard creditCard = CreditCard.builder()
                .name("Tim")
                .cardNumber(73342639365L)
                .limit(Double.valueOf(300000))
                .balance(Double.valueOf(100)).build();

        Assertions.assertAll(() -> {
            assertEquals("Tim", creditCard.getName(), "Name on Card does not match");
            assertEquals(73342639365L, creditCard.getCardNumber(), "Card Number does not match");
            assertEquals(Double.valueOf(300000), creditCard.getLimit(), "Card Limit does not match");
            assertEquals(Double.valueOf(100), creditCard.getBalance(), "Card Balance doe not match");
        });
    }
}
