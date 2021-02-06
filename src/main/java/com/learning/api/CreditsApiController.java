package com.learning.api;

import com.learning.model.CreditCardDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${open-api.ccp.base.path:ccp}")
public class CreditsApiController implements CreditsApi {

    private static String X_CORRELATION_ID = "X-Correlation-Id";

    @Override
    public ResponseEntity addCreditCard(String xCorrelationId, @Valid CreditCardDetails creditCardDetails) {
        return ResponseEntity.status(HttpStatus.OK).header(X_CORRELATION_ID,xCorrelationId).build();
    }

    @Override
    public ResponseEntity<List<CreditCardDetails>> getCreditCards(String xCorrelationId) {
        //Temporary implementation. Add correct implementation here
        CreditCardDetails ccd = new CreditCardDetails();
        ccd.setName("Tim");
        ccd.setCardNumber(73342639365L);
        ccd.setLimit(Double.valueOf(300000));
        ccd.setBalance(Double.valueOf(100));

        return ResponseEntity.status(HttpStatus.OK).header(X_CORRELATION_ID,xCorrelationId).body(List.of(ccd));
    }
}
