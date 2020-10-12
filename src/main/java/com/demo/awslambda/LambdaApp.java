package com.demo.awslambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.businesslogic.Currency;
import com.serverless.businesslogic.CurrencyConverter;

/**
 * Hello world!
 */
public class LambdaApp implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent s, Context context) {
        System.out.println("INSIDE LAMBDA " + s.toString());
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("INSIDE BODY " + s.getBody());
            Currency inputCurrency = mapper.readValue(s.getBody(), Currency.class);
            System.out.println("INSIDE TRY " + s.getBody());
            if (inputCurrency != null) {
                //Business Logic
                CurrencyConverter logic = new CurrencyConverter();
                Currency outputCurrency = logic.convert(inputCurrency);
                responseEvent.setBody(mapper.writeValueAsString(outputCurrency));
                responseEvent.setStatusCode(200);
                System.out.println("ALMOST FINISHED " + outputCurrency);
            } else {
                responseEvent.setStatusCode(200);
                responseEvent.setBody("ERROR !");
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.getMessage());
        }
        return responseEvent;
    }
}
