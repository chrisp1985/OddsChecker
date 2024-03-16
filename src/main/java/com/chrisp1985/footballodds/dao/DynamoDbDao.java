package com.chrisp1985.footballodds.dao;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.chrisp1985.footballodds.configuration.DynamoClientConfig;
import com.chrisp1985.footballodds.dto.DynamoDb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
public class DynamoDbDao implements StorageDao{
    private static final String DYNAMODB_TABLE_NAME = "FootballResults_tf";

    private DynamoClientConfig dynamoClientConfig;

    public DynamoDbDao(DynamoClientConfig dynamoClientConfig) {

        this.dynamoClientConfig = dynamoClientConfig;

    }

    public void add(DynamoDb classToAdd) {
        try {

            dynamoClientConfig.getDdbClient().putItem(DYNAMODB_TABLE_NAME,
                    new DynamoDBMapper(dynamoClientConfig.getDdbClient()).getTableModel(DynamoDb.class).convert(classToAdd));

        } catch (AmazonServiceException e) {

            log.error("Found an error: {}. Be sure that it exists and that you've typed its name correctly!", e.getErrorMessage());
            System.exit(1);

        }
    }

    public void update(String tableName,
                       Map<String, AttributeValue> key,
                       Map<String, AttributeValueUpdate> attributeUpdates) {
        try {

            dynamoClientConfig.getDdbClient().updateItem(tableName, key, attributeUpdates);

        } catch (AmazonServiceException e) {

            log.error("Found an error: {}. Be sure that it exists and that you've typed its name correctly!", e.getErrorMessage());
            System.exit(1);

        }
    }
}
