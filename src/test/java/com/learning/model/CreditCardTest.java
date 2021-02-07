package com.learning.model;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;

@ExtendWith(SpringExtension.class)
public class CreditCardTest {

    @Test
    public void testCreditCard() {
        CreditCard creditCard = CreditCard.builder()
                .name("Tim")
                .cardNumber(new BigInteger("73342639365"))
                .limit(BigDecimal.valueOf(300000))
                .balance(BigDecimal.valueOf(100)).build();

        Assertions.assertAll(() -> {
            assertEquals("Tim", creditCard.getName(), "Name on Card does not match");
            assertEquals(73342639365L, creditCard.getCardNumber().longValue(), "Card Number does not match");
            assertEquals(Double.valueOf(300000), creditCard.getLimit().doubleValue(), "Card Limit does not match");
            assertEquals(Double.valueOf(100), creditCard.getBalance().doubleValue(), "Card Balance doe not match");
        });
    }
}
