package com.learning.service;

import com.learning.model.CreditCardDetails;

import java.util.List;

public interface CardProcessingService {

    /**
     * To Add Credit card details to the store
     * @param creditCardDetails
     */
    public void addCredits(CreditCardDetails creditCardDetails);

    /**
     * To get all the Credit card details
     * @return list of Credit card details
     */
    public List<CreditCardDetails> getAllCreditCardDetails();
}
