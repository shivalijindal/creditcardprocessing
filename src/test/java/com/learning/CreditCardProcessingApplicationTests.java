package com.learning;

import com.learning.api.CreditsApiController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreditCardProcessingApplicationTests {

	@Autowired
	CreditsApiController creditsApiController;

	@DisplayName("Test Controller Injection Before Test Run")
	@Test
	void contextLoads() {
		Assertions.assertNotNull(creditsApiController);
	}

}
