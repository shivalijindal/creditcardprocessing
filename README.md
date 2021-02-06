# Credit Card Processing Application

CCP is a Spring Boot Application developed and compiled using JDK 11.
The Rest API follow the Contract-First Approach. The documentation is written manually as a yaml (Check src/main/resources/creditcardprovider.yaml).
The document is used to generate the API interfaces which are then implemented.


# Setup and deployment

Please find below the steps to setup and deploy the application:
1. Ensure that you have JDK 11 on your system.
2. Build the project using 'mvn clean install'. As part of the build, the maven plugin openapi-generator-plugin is going to read the api documentation and generate the api interfaces and the model classes in the target folder.
If you are using an IDE, ensure that you add/mark the generated-sources directory under target as a source since you would want the generated classes to be read and compiled in the IDE.
3. Start the spring boot application either by running the CreditCardProcessingApplication.java class directly or using the command 'mvn spring-boot:run'.
The application would start on port 8081 (This can be identified using the logs).

4. Check Url: http://localhost:8081/actuator/health for health of the service.
5. Check Url: http://localhost:8081/ccp/credits as POST for adding new credit and as GET for getting all credits.



