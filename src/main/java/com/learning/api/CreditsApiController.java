package com.learning.api;

import com.learning.model.CreditCardDetails;
import com.learning.service.CardProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${open-api.ccp.base.path:ccp}")
public class CreditsApiController implements CreditsApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditsApiController.class);

    @Autowired
    private CardProcessingService cardProcessingService;

    private static final String X_CORRELATION_ID = "X-Correlation-Id";

    @Override
    public ResponseEntity addCreditCard(String xCorrelationId, @Valid CreditCardDetails creditCardDetails) {
        LOGGER.info("Add Credit Request Received: {}", creditCardDetails);
        cardProcessingService.addCredits(creditCardDetails);
        LOGGER.info("Credit Card Details Added");
        return ResponseEntity.status(HttpStatus.OK).header(X_CORRELATION_ID,xCorrelationId).build();
    }

    @Override
    public ResponseEntity<List<CreditCardDetails>> getCreditCards(String xCorrelationId) {
        LOGGER.info("Get All Credits Request Received");
        List<CreditCardDetails> cardDetailList = cardProcessingService.getAllCreditCardDetails();
        LOGGER.info("Get All Credits Response: {}", cardDetailList);
        return ResponseEntity.status(HttpStatus.OK).header(X_CORRELATION_ID,xCorrelationId).body(cardDetailList);
    }
}
