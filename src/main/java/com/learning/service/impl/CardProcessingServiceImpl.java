package com.learning.service.impl;

import com.learning.model.CreditCard;
import com.learning.model.CreditCardDetails;
import com.learning.repository.CardProcessingRepository;
import com.learning.service.CardProcessingService;
import com.learning.validation.CardValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CardProcessingServiceImpl is the implementation that provides adding and getting credit card
 * details from the database.
 */
@Service
public class CardProcessingServiceImpl implements CardProcessingService {

    @Autowired
    CardProcessingRepository cardProcessingRepository;

    @Autowired
    CardValidationService cardValidationService;

    /**
     * To Add Credit card details to the store
     * @param creditCardDetails
     */
    @Override
    public void addCredits(CreditCardDetails creditCardDetails) {
        validateCard(creditCardDetails);

        CreditCard creditCard = mapCreditDetailsToDBEntity(creditCardDetails);
        cardProcessingRepository.saveAndFlush(creditCard);
    }

    /**
     * To get all the Credit card details
     * @return list of Credit card details
     */
    @Override
    public List<CreditCardDetails> getAllCreditCardDetails() {
        List<CreditCard> creditCardList = cardProcessingRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return mapDBEntityToCreditCardDetails(creditCardList);
    }

    /**
     * Validates Card Number
     * 1. Checks if the card Number has no more than 19 digits
     * 2. Applies Luhn 10 validation on card Number
     * 3. Check for duplicate entries in the card
     *
     * @param creditCardDetails
     */
    private void validateCard(CreditCardDetails creditCardDetails) {
        //Check for 19 digits i cardNumber
        int digits = creditCardDetails.getCardNumber().toString().length();
        if (digits>19)
            throw new IllegalArgumentException("Card Number is greater that 19 digits");

        //Luhn10 Check
        boolean isCardNumberValid = cardValidationService.isCreditCardValid(creditCardDetails.getCardNumber());
        if (!isCardNumberValid)
            throw new IllegalArgumentException("Card Number Failed Luhn10 Check");

        //Check for duplicate entry.
        Optional<CreditCard> creditCardList = cardProcessingRepository.findByCardNumber(creditCardDetails.getCardNumber());
        if (creditCardList.isPresent())
            throw new IllegalArgumentException("Card Number Already exists in database");
    }

    /**
     * Maps the DB Entity class List to the API Model Class List.
     * @param creditCardList
     * @return API Model Class List Credit Card Details
     */
    private List<CreditCardDetails> mapDBEntityToCreditCardDetails(List<CreditCard> creditCardList) {
        List<CreditCardDetails> creditCardDetailsList = new ArrayList<>();
        creditCardList.forEach(creditCard -> {
            CreditCardDetails cardDetails = new CreditCardDetails();
            cardDetails.name(creditCard.getName())
                    .cardNumber(creditCard.getCardNumber())
                    .limit(creditCard.getLimit())
                    .balance(creditCard.getBalance());
            creditCardDetailsList.add(cardDetails);
        });
        return creditCardDetailsList;
    }

    /**
     * Maps the API Model Class  to the DB Entity Class.
     * @param creditCardDetails
     * @return DB Entity Class CreditCard
     */
    private CreditCard mapCreditDetailsToDBEntity(CreditCardDetails creditCardDetails) {
        return CreditCard.builder()
                .name(creditCardDetails.getName())
                .cardNumber(creditCardDetails.getCardNumber())
                .limit(creditCardDetails.getLimit())
                .balance(creditCardDetails.getBalance()).build();
    }

}
