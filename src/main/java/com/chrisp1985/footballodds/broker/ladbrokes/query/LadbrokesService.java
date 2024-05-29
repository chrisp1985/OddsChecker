package com.chrisp1985.footballodds.broker.ladbrokes.query;

import com.chrisp1985.footballodds.broker.ladbrokes.client.LadbrokesClient;
import com.chrisp1985.footballodds.broker.ladbrokes.conversion.LadbrokesEventToDynamo;
import com.chrisp1985.footballodds.dto.DynamoDb;
import com.chrisp1985.footballodds.broker.ladbrokes.model.enums.OutcomeMeaningMinorCode;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.EventChild;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.ResponseModel;
import com.chrisp1985.footballodds.service.storage.StoreOddsService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@ConditionalOnProperty(value = "application.broker", havingValue = "Ladbrokes")
public class LadbrokesService {

    private final LadbrokesClient brokerClient;

    private final StoreOddsService storeOddsService;

    private final LadbrokesEventToDynamo ladbrokesEventToDynamo;

    private final LadbrokesResponseParser ladbrokesResponseParser;

    @Autowired
    public LadbrokesService(LadbrokesClient brokerClient, StoreOddsService storeOddsService,
                            LadbrokesEventToDynamo ladbrokesEventToDynamo, LadbrokesResponseParser ladbrokesResponseParser) {
        this.brokerClient = brokerClient;
        this.storeOddsService = storeOddsService;
        this.ladbrokesEventToDynamo = ladbrokesEventToDynamo;
        this.ladbrokesResponseParser = ladbrokesResponseParser;

    }

    @PostConstruct
    public void queryEndpointAndStoreValues() {

        List<EventChild> validEvents = getValidEvents(brokerClient.getOddsData());
        List<DynamoDb> dtos = validEvents.stream().map(ladbrokesEventToDynamo::buildDynamoDto).toList();
        storeOddsService.saveToDynamo(dtos);

    }

    private List<EventChild> getValidEvents(ResponseModel response) {

        return ladbrokesResponseParser.getAvailableEvents(response).stream()
                .filter(this::areOddsGoodValue).toList();

    }

    public boolean areOddsGoodValue(EventChild event) {

        double homeOdds = ladbrokesResponseParser.returnPriceForMeaningCode(
                OutcomeMeaningMinorCode.H, ladbrokesResponseParser.getMarketChild(event, "|Match Result|"));
        double awayOdds = ladbrokesResponseParser.returnPriceForMeaningCode(
                OutcomeMeaningMinorCode.A, ladbrokesResponseParser.getMarketChild(event, "|Match Result|"));
        double drawOdds = ladbrokesResponseParser.returnPriceForMeaningCode(
                OutcomeMeaningMinorCode.D, ladbrokesResponseParser.getMarketChild(event, "|Match Result|"));

        log.info("Home Odds: {}, Draw Odds: {}, Away Odds: {}", homeOdds, drawOdds, awayOdds);
        return homeOdds > 4 && awayOdds < 1.9;

    }

}
