package com.learning.service;

import com.learning.model.CreditCard;
import com.learning.model.CreditCardDetails;
import com.learning.repository.CardProcessingRepository;
import com.learning.service.impl.CardProcessingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CardProcessingServiceTest {

    @InjectMocks
    CardProcessingServiceImpl cardProcessingService;

    @Mock
    private CardProcessingRepository cardProcessingRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Assertions.assertNotNull(cardProcessingService);
        Assertions.assertNotNull(cardProcessingRepository); //Check Mock Injection
    }

    @Test
    public void testAddCredits() {
        //Create Input data
        CreditCardDetails cardDetails = new CreditCardDetails();
        cardDetails.name("Hillary")
                .cardNumber(9383645284640L)
                .limit(Double.valueOf(30000))
                .balance(Double.valueOf(100));

        //Test method call
        cardProcessingService.addCredits(cardDetails);

        //Verify
        Mockito.verify(cardProcessingRepository, VerificationModeFactory.times(1)).saveAndFlush(Mockito.any());
    }

    @Test
    public void testGetAllCredits() {
        //Sample Data from database
        CreditCard creditCard = CreditCard.builder()
                .name("Tim")
                .cardNumber(73342639365L)
                .limit(Double.valueOf(300000))
                .balance(Double.valueOf(100)).build();

        //Mocking Database query call
        Mockito.when(cardProcessingRepository.findAll(Mockito.any(Sort.class))).thenReturn(List.of(creditCard));

        //Test Method call
        List<CreditCardDetails> creditCardDetailsList = cardProcessingService.getAllCreditCardDetails();

        //Assertions and Verify calls
        Mockito.verify(cardProcessingRepository, VerificationModeFactory.times(1)).findAll(Mockito.any(Sort.class));
        assertEquals(1, creditCardDetailsList.size(), "Size of the list does not match");
        Assertions.assertAll(() -> {
            assertEquals("Tim", creditCardDetailsList.get(0).getName(), "Name on Card does not match");
            assertEquals(73342639365L, creditCardDetailsList.get(0).getCardNumber(), "Card Number does not match");
            assertEquals(Double.valueOf(300000), creditCardDetailsList.get(0).getLimit(), "Card Limit does not match");
            assertEquals(Double.valueOf(100), creditCardDetailsList.get(0).getBalance(), "Card Balance doe not match");
        });
    }
}
