package com.chrisp1985.footballodds;

import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamLambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(FootballoddsApplication.class);
        } catch (Exception e) {
            // Upsettingly, we just ignore here. The following 2 lines should have been run:

            //e.printStackTrace();
            //throw new RuntimeException("Could not initialize Spring Boot application", e);

            // Exception swallowed because there's an exception is shown here for:
            // org.springframework.context.annotation.AnnotationConfigApplicationContext and
            // org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext
            // are in unnamed module of loader com.amazonaws.services.lambda.runtime.api.client.CustomerClassLoader
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        //handler.proxyStream(inputStream, outputStream, context);

        // Because of the exception above being swallowed, handler is null at this point, so the method call fails.
        // The action for the lambda is actually going on when the Application.class gets called so everything has
        // already been run by this point anyway.

    }
}
