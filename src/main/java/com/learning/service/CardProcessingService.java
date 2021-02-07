package com.learning.service;

import com.learning.model.CreditCardDetails;

import java.util.List;

/**
 * CardProcessingService provides adding and getting credit card details from the database.
 */
public interface CardProcessingService {

    /**
     * To Add Credit card details to the store
     * @param creditCardDetails
     */
    void addCredits(CreditCardDetails creditCardDetails);

    /**
     * To get all the Credit card details
     * @return list of Credit card details
     */
    List<CreditCardDetails> getAllCreditCardDetails();
}
