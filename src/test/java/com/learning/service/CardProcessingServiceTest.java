package com.learning.service;

import com.learning.model.CreditCard;
import com.learning.model.CreditCardDetails;
import com.learning.repository.CardProcessingRepository;
import com.learning.service.impl.CardProcessingServiceImpl;
import com.learning.validation.CardValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class CardProcessingServiceTest {

    @InjectMocks
    CardProcessingServiceImpl cardProcessingService;

    @Mock
    private CardProcessingRepository cardProcessingRepository;

    @Mock
    private CardValidationService cardValidationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Assertions.assertNotNull(cardProcessingService);
        Assertions.assertNotNull(cardProcessingRepository); //Check Mock Injection
        Assertions.assertNotNull(cardValidationService); //Check Mock Injection
    }

    @DisplayName("Test Add Credit Service")
    @Test
    public void testAddCredits() {
        //Create Input data
        CreditCardDetails cardDetails = new CreditCardDetails();
        cardDetails.name("Hillary")
                .cardNumber(new BigInteger("9383645284640"))
                .limit(BigDecimal.valueOf(30000))
                .balance(BigDecimal.valueOf(100));

        //Mocking
        Mockito.when(cardProcessingRepository.findByCardNumber(Mockito.any(BigInteger.class))).thenReturn(Optional.empty());
        Mockito.when(cardValidationService.isCreditCardValid(Mockito.any(BigInteger.class))).thenReturn(true);

        //Test method call
        cardProcessingService.addCredits(cardDetails);

        //Verify
        Mockito.verify(cardProcessingRepository, VerificationModeFactory.times(1)).saveAndFlush(Mockito.any());
        Mockito.verify(cardValidationService, VerificationModeFactory.times(1)).isCreditCardValid(Mockito.any());
    }

    @DisplayName("Test Get All Credits Service")
    @Test
    public void testGetAllCredits() {
        //Sample Data from database
        CreditCard creditCard = CreditCard.builder()
                .name("Tim")
                .cardNumber(new BigInteger("73342639365"))
                .limit(BigDecimal.valueOf(300000))
                .balance(BigDecimal.valueOf(100)).build();

        //Mocking Database query call
        Mockito.when(cardProcessingRepository.findAll(Mockito.any(Sort.class))).thenReturn(List.of(creditCard));

        //Test Method call
        List<CreditCardDetails> creditCardDetailsList = cardProcessingService.getAllCreditCardDetails();

        //Assertions and Verify calls
        Mockito.verify(cardProcessingRepository, VerificationModeFactory.times(1)).findAll(Mockito.any(Sort.class));
        assertEquals(1, creditCardDetailsList.size(), "Size of the list does not match");
        Assertions.assertAll(() -> {
            assertEquals("Tim", creditCardDetailsList.get(0).getName(), "Name on Card does not match");
            assertEquals(73342639365L, creditCardDetailsList.get(0).getCardNumber().longValue(), "Card Number does not match");
            assertEquals(Double.valueOf(300000), creditCardDetailsList.get(0).getLimit().doubleValue(), "Card Limit does not match");
            assertEquals(Double.valueOf(100), creditCardDetailsList.get(0).getBalance().doubleValue(), "Card Balance doe not match");
        });
    }

    @DisplayName("Test Add Credit Service with invalid card number that fails Luhn10")
    @Test
    public void testAddCredits_CardValidationLuhn10Failure() {
        //Create Input data
        CreditCardDetails cardDetails = new CreditCardDetails();
        cardDetails.name("Hillary")
                .cardNumber(new BigInteger("9383645284640"))
                .limit(BigDecimal.valueOf(30000))
                .balance(BigDecimal.valueOf(100));

        //Mocking validation call
        Mockito.when(cardValidationService.isCreditCardValid(Mockito.any(BigInteger.class))).thenReturn(false);

        //Test method call
        assertThrows(IllegalArgumentException.class,
                ()->{
                    cardProcessingService.addCredits(cardDetails);
                });

        //Verify call
        Mockito.verify(cardValidationService, VerificationModeFactory.times(1)).isCreditCardValid(Mockito.any());
    }

    @DisplayName("Test Add Credit Service with duplicate card number")
    @Test
    public void testAddCredits_DuplicateCard() {
        //Create Input data
        CreditCardDetails cardDetails = new CreditCardDetails();
        cardDetails.name("Hillary")
                .cardNumber(new BigInteger("9383645284640"))
                .limit(BigDecimal.valueOf(30000))
                .balance(BigDecimal.valueOf(100));

        //Mocking validation call
        Mockito.when(cardValidationService.isCreditCardValid(Mockito.any(BigInteger.class))).thenReturn(true);
        Mockito.when(cardProcessingRepository.findByCardNumber(Mockito.any(BigInteger.class))).thenReturn(Optional.empty());

        //Test method call
        cardProcessingService.addCredits(cardDetails);

        //Verify
        Mockito.verify(cardProcessingRepository, VerificationModeFactory.times(1)).saveAndFlush(Mockito.any());
        Mockito.verify(cardValidationService, VerificationModeFactory.times(1)).isCreditCardValid(Mockito.any());

        Mockito.when(cardProcessingRepository.findByCardNumber(Mockito.any(BigInteger.class))).thenReturn(Optional.of(new CreditCard()));
        //Test method call - Duplicate
        assertThrows(IllegalArgumentException.class,
                ()->{
                    cardProcessingService.addCredits(cardDetails);
                });
    }
}
