package com.learning.service.impl;

import com.learning.model.CreditCard;
import com.learning.model.CreditCardDetails;
import com.learning.repository.CardProcessingRepository;
import com.learning.service.CardProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardProcessingServiceImpl implements CardProcessingService {

    @Autowired
    CardProcessingRepository cardProcessingRepository;

    @Override
    public void addCredits(CreditCardDetails creditCardDetails) {
        CreditCard creditCard = mapCreditDetailsToDBEntity(creditCardDetails);
        cardProcessingRepository.saveAndFlush(creditCard);
    }

    @Override
    public List<CreditCardDetails> getAllCreditCardDetails() {
        List<CreditCard> creditCardList = cardProcessingRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return mapDBEntityToCreditCardDetails(creditCardList);
    }

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

    private CreditCard mapCreditDetailsToDBEntity(CreditCardDetails creditCardDetails) {
        return CreditCard.builder()
                .name(creditCardDetails.getName())
                .cardNumber(creditCardDetails.getCardNumber())
                .limit(creditCardDetails.getLimit())
                .balance(creditCardDetails.getBalance()).build();
    }

}
