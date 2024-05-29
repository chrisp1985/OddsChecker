package com.chrisp1985.footballodds.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "${application.dynamo.tablename}")
public class DynamoDb {

    @DynamoDBHashKey
    @DynamoDBAttribute
    public String event_id;

    @DynamoDBAttribute
    public String event_name;

    @DynamoDBAttribute
    public Double home_odds;

    @DynamoDBAttribute
    public Double away_odds;

    @DynamoDBAttribute
    public Double draw_odds;

    @DynamoDBAttribute
    public Double double_chance_away_odds;

    @DynamoDBAttribute
    public Double double_chance_home_odds;

    @DynamoDBAttribute
    public String start_time;

    @DynamoDBAttribute
    public String type_name;

    @DynamoDBAttribute
    public String class_name;

    @DynamoDBAttribute
    public String broker_name;

    @DynamoDBAttribute
    public String results_timestamp;
}