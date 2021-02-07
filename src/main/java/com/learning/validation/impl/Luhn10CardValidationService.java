package com.learning.validation.impl;

import com.learning.validation.CardValidationService;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static java.math.BigInteger.TEN;

/**
 * Luhn10CardValidationService validates Card Number.
 * Validation -
 * Sum all the digits in the card Number in the below manner:
 * 1. Sum all digits in odd places
 * 2. Double the digits in even places. If the doubled value is greater than/ equal to 10, Add the digits
 * of the doubled value and add that value to the sum else add the doubled value directly to the sum.
 *
 * The digit at ones place is called checksum. Its value should help the entire sum (excluding the last digit) to be divisible by 10.
 *
 * The total sum of all the odd and doubled even digits should be divisible by 10.
 *
 */
@Component
public class Luhn10CardValidationService implements CardValidationService {


    /**
     * Validates card based on Luhn10 validation logic.
     * @param cardNumber
     * @return
     */
    @Override
    public boolean isCreditCardValid(final BigInteger cardNumber) {
        int counter = 0; // To check even/odd digit
        int sum=0;
        BigInteger cardNumberToValidate = cardNumber;
        while (!cardNumberToValidate.equals(BigInteger.ZERO)){
            counter++;
            int digit = cardNumberToValidate.mod(TEN).intValue();
            if (counter%2==0) {
                int doubledValue = digit*2;
                sum+= doubledValue/10 + doubledValue%10;
            } else {
                sum+= digit;
            }
            cardNumberToValidate = cardNumberToValidate.divide(TEN);
        }
        return sum%10==0;
    }
}
