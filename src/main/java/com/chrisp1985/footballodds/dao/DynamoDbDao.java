package com.chrisp1985.footballodds.dao;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.chrisp1985.footballodds.dto.DynamoDb;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class DynamoDbDao implements StorageDao{
    private static final String DYNAMODB_TABLE_NAME = "FootballResults_tf";

    public void add(DynamoDb classToAdd, AmazonDynamoDB client) {
        try {

            client.putItem(DYNAMODB_TABLE_NAME,
                    new DynamoDBMapper(client).getTableModel(DynamoDb.class).convert(classToAdd));

        } catch (AmazonServiceException e) {

            log.error("Found an error: {}. Be sure that it exists and that you've typed its name correctly!", e.getErrorMessage());
            System.exit(1);

        }
    }

    public void update(AmazonDynamoDB client, String tableName,
                       Map<String, AttributeValue> key,
                       Map<String, AttributeValueUpdate> attributeUpdates) {
        try {

            client.updateItem(tableName, key, attributeUpdates);

        } catch (AmazonServiceException e) {

            log.error("Found an error: {}. Be sure that it exists and that you've typed its name correctly!", e.getErrorMessage());
            System.exit(1);

        }
    }
}
