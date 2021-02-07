package com.learning.validation;

import java.math.BigInteger;

public interface CardValidationService {

    boolean isCreditCardValid(BigInteger cardNumber);

}
