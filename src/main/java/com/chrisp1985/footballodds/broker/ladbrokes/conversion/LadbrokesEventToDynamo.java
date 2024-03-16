package com.chrisp1985.footballodds.broker.ladbrokes.conversion;

import com.chrisp1985.footballodds.broker.ladbrokes.model.response.EventChild;
import com.chrisp1985.footballodds.dto.DynamoDb;
import com.chrisp1985.footballodds.broker.ladbrokes.model.enums.OutcomeMeaningMinorCode;
import com.chrisp1985.footballodds.broker.ladbrokes.query.LadbrokesResponseParser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service
public class LadbrokesEventToDynamo {

    @Value("${application.broker}")
    private String applicationBroker;

    DynamoDb.DynamoDbBuilder dynamoDbBuilder;

    EventChild event;

    private final LadbrokesResponseParser ladbrokesResponseParser;

    @Autowired
    public LadbrokesEventToDynamo(LadbrokesResponseParser ladbrokesResponseParser) {
        this.dynamoDbBuilder = DynamoDb.builder();
        this.ladbrokesResponseParser = ladbrokesResponseParser;
    }

    public DynamoDb buildDynamoDto(EventChild event) {
        this.event = event;
        return dynamoDbBuilder
                .event_name(event.event.name.replace("|", ""))
                .event_id(event.event.id)
                .home_odds(Double.valueOf(ladbrokesResponseParser.getOdds(event, OutcomeMeaningMinorCode.H)))
                .draw_odds(Double.valueOf(ladbrokesResponseParser.getOdds(event, OutcomeMeaningMinorCode.D)))
                .away_odds(Double.valueOf(ladbrokesResponseParser.getOdds(event, OutcomeMeaningMinorCode.A)))
                .type_name(event.event.typeName.replace("|", ""))
                .class_name(event.event.className.replace("|", ""))
                .start_time(event.event.startTime)
                .broker_name(applicationBroker)
                .build();
    }

}
