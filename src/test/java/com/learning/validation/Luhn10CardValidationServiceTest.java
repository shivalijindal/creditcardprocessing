package com.learning.validation;

import com.learning.validation.impl.Luhn10CardValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;

@ExtendWith(SpringExtension.class)
public class Luhn10CardValidationServiceTest {

    @Test
    public void testisCreditCardValidWithValidData(){
        Luhn10CardValidationService validation = new Luhn10CardValidationService();
        BigInteger bigInteger = BigInteger.valueOf(12345674);
        Assertions.assertTrue(validation.isCreditCardValid(bigInteger));
    }

    @Test
    public void testisCreditCardValidWithInvalidData(){
        Luhn10CardValidationService validation = new Luhn10CardValidationService();
        Assertions.assertFalse(validation.isCreditCardValid(BigInteger.valueOf(13245674)));
    }
}
