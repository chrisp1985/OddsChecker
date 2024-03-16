package com.chrisp1985.footballodds.service.storage;

import com.chrisp1985.footballodds.configuration.DynamoClientConfig;
import com.chrisp1985.footballodds.broker.ladbrokes.conversion.LadbrokesEventToDynamo;
import com.chrisp1985.footballodds.dao.DynamoDbDao;
import com.chrisp1985.footballodds.dto.DynamoDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class StoreOddsService {

    ObjectMapper objectMapper;

    DynamoDbDao dynamoDbDao;

    @Autowired
    public StoreOddsService(ObjectMapper objectMapper, DynamoDbDao dynamoDbDao) {
        this.objectMapper = objectMapper;
        this.dynamoDbDao = dynamoDbDao;
    }

    public void saveToDynamo(List<DynamoDb> dtos) {

        printAllDataBeingSavedToDynamo(dtos);
        dtos.forEach(this::pushDtoToDynamoDb);

    }

    public void printAllDataBeingSavedToDynamo(List<DynamoDb> dtos) {

        DecimalFormat format = new DecimalFormat("0.#");

        dtos.forEach(dynamoDb -> System.out.printf("%s : %s / %s / %s : %s\n",
                dynamoDb.event_name,
                format.format(dynamoDb.home_odds),
                format.format(dynamoDb.draw_odds),
                format.format(dynamoDb.away_odds),
                dynamoDb.start_time));

    }

    private void pushDtoToDynamoDb(DynamoDb dto) {
        dynamoDbDao.add(dto);
    }
}
