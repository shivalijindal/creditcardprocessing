# Credit Card Processing Application

CCP is a Spring Boot Application developed and compiled using JDK 11.
The Rest APIs are created using openAPI documentation and they follow the Contract-First Approach. The documentation is written manually as a yaml (Check src/main/resources/creditcardprovider.yaml) and is used to generate the API interfaces which are then implemented.


# Setup and deployment

Please find below the steps to setup and deploy the application:
1. Ensure that you have JDK 11 on your system.
2. Build the project using 'mvn clean install'. As part of the build, the maven plugin openapi-generator-plugin is going to read the api documentation and generate the api interfaces and the model classes in the target folder.
If you are using an IDE, ensure that you add/mark the generated-sources directory under target as a source since you would want the generated classes to be read and compiled in the IDE.
3. Start the spring boot application either by running the CreditCardProcessingApplication.java class directly or using the command 'mvn spring-boot:run'.
The application would start on port 8081 (This can be identified using the logs).

# Test
1. Check Url: http://localhost:8081/actuator/health for health of the service.
2. Test Urls as below:
a) http://localhost:8081/ccp/credits as POST for adding new credit.

   Mandatory headers: X-Correlation-Id for tracking purpose.

   Request Body(Sample):
        {
          "name": "Alice",
          "cardNumber": 13245675,
          "limit": 100000,
          "balance": 10000
        }

   Response Body(Sample):
   200 OK - No Response Body

   400/415/500 -
        {
          "reasonCode": "1001",
          "description": "Invalid Data Received",
          "source": "CCP"
        }

b) http://localhost:8081/ccp/credits as GET for getting all credits.

   Mandatory headers: X-Correlation-Id for tracking purpose.

   Request Body: No Body Required.

   Response Body:
   200 OK -
    [
      {
        "name": "John",
        "cardNumber": 654428635281,
        "limit": 100000,
        "balance": 10000
      }
      {
        "name": "Alice",
        "cardNumber": 13245675,
        "limit": 100000,
        "balance": 10000
      }
    ]

   400/415/500 -
    {
      "reasonCode": "1001",
      "description": "Invalid Data Received",
      "source": "CCP"
    }

c) The database can be reached using http://localhost:8081/h2-console and below DB details:

    url: jdbc:h2:mem:db
    driverClassName: org.h2.Driver
    username: sa
    password: password



