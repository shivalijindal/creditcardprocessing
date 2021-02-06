package com.learning;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.api.CreditsApiController;
import com.learning.model.CreditCardDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CreditCardProcessingApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	CreditsApiController creditsApiController;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	private static String HOST = "http://localhost:";

	@DisplayName("Test Controller Injection Before Test Run")
	@Test
	void contextLoads() {
		Assertions.assertNotNull(creditsApiController);
		Assertions.assertNotNull(objectMapper);
		Assertions.assertNotNull(restTemplate);
	}

	@Test
	public void testAddCredits() {
		//Create Test Data
		CreditCardDetails creditCard = new CreditCardDetails();
		creditCard.name("Tim")
				.cardNumber(73342639365L)
				.limit(Double.valueOf(300000))
				.balance(Double.valueOf(100));

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Correlation-Id", "945795846");
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CreditCardDetails> request = new HttpEntity(creditCard, headers);
		ResponseEntity responseEntity = restTemplate.postForEntity(HOST+port+"/ccp/credits",request,Object.class);
		Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode(),"Http Status Code does not match");
	}

	@Test
	public void testGetAllCredits() {
		//Create Test Data

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Correlation-Id", "945795846");
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		HttpEntity request = new HttpEntity(headers);

		ResponseEntity<List<CreditCardDetails>> responseEntity = restTemplate.exchange(HOST+port+"/ccp/credits",HttpMethod.GET,request, new ParameterizedTypeReference<List<CreditCardDetails>>() {});
		Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode(),"Http Status Code does not match");
		Assertions.assertEquals(1,responseEntity.getHeaders().get("X-Correlation-Id").size(), "CorrelationId not present");
		Assertions.assertEquals("945795846",responseEntity.getHeaders().get("X-Correlation-Id").get(0), "CorrelationId not present");
		Assertions.assertTrue(responseEntity.getBody().size()>0, "Response List is empty");
		CreditCardDetails creditCardDetails = responseEntity.getBody().get(0);

		Assertions.assertAll(() -> {
			assertEquals("Alice", creditCardDetails.getName(), "Name on Card does not match");
			assertEquals(56728465133L, creditCardDetails.getCardNumber(), "Card Number does not match");
			assertEquals(Double.valueOf(100000), creditCardDetails.getLimit(), "Card Limit does not match");
			assertEquals(Double.valueOf(0), creditCardDetails.getBalance(), "Card Balance doe not match");
		});
	}

}
