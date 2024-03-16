package com.chrisp1985.footballodds.configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoClientConfig {

    private static final Regions AWS_REGION = Regions.EU_WEST_2;

    private AmazonDynamoDB ddbClient;

    public DynamoClientConfig() {
        this.ddbClient = buildAmazonDynamoDbClient();
    }

    public AmazonDynamoDB getDdbClient() {
        if (this.ddbClient == null) {
            this.ddbClient = buildAmazonDynamoDbClient();
        }
        return this.ddbClient;
    }

    private AmazonDynamoDB buildAmazonDynamoDbClient() {
        AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard();
        clientBuilder.setRegion(AWS_REGION.getName());
        clientBuilder.setCredentials(new AwsCredentialsConfig().getAwsCredentials());
        return clientBuilder.build();
    }
}