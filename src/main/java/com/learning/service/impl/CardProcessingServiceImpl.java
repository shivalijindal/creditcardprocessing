package com.learning.service.impl;

import com.learning.model.CreditCardDetails;
import com.learning.service.CardProcessingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardProcessingServiceImpl implements CardProcessingService {

    @Override
    public void addCredits(CreditCardDetails creditCardDetails) {
        //Add correct implementation
        System.out.println("Added elements");
    }

    @Override
    public List<CreditCardDetails> getAllCreditCardDetails() {
        //Temporary implementation. Add correct implementation here
        CreditCardDetails ccd = new CreditCardDetails();
        ccd.setName("Tim");
        ccd.setCardNumber(73342639365L);
        ccd.setLimit(Double.valueOf(300000));
        ccd.setBalance(Double.valueOf(100));
        return List.of(ccd);
    }
}
