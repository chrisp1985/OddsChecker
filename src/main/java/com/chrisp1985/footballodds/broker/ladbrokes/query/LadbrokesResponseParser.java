package com.chrisp1985.footballodds.broker.ladbrokes.query;

import com.chrisp1985.footballodds.broker.ladbrokes.model.enums.OutcomeMeaningMinorCode;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.EventChild;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.MarketChild;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LadbrokesResponseParser {

    public Double returnPriceForMeaningCode(OutcomeMeaningMinorCode code, MarketChild event) {

        return Double.valueOf(event.market.children.stream()
                .filter(child -> child.outcome.outcomeMeaningMinorCode.equals(code.name()))
                       // .peek(a -> log.info("Found: {}", a)) // Debug example.
                .map(child -> child.outcome.children.get(0).price.priceDec).toList().get(0));
    }

    public MarketChild getMarketChild(EventChild event, String marketName) {

        return event.event.children.stream()
                .filter(marketChild -> marketChild.market.name.equals(marketName))
                .toList().get(0);
    }

    public List<EventChild> getAvailableEvents(ResponseModel response) {

        return getEventChildrenFromResponse(response);

    }

    public List<EventChild> getEventChildrenFromResponse(ResponseModel response) {

        return Optional.ofNullable(response)
                .map(ssResponse -> ssResponse.getSSResponse().children)
                .filter(children -> !children.isEmpty())
                .map(children -> children.stream()
                        .filter(child -> Objects.nonNull(child.event))
                        .collect(Collectors.toList()))
                .orElse(List.of());

    }

    public String getMatchResultOdds(EventChild eventChild, OutcomeMeaningMinorCode code) {
        return getOdds(eventChild, code.name(), "|Match Result|");
    }

    public String getAwayTeamDoubleChanceOdds(EventChild eventChild) {
        return getOdds(eventChild, String.valueOf(2), "|Double Chance|");
    }

    public String getHomeTeamDoubleChanceOdds(EventChild eventChild) {
        return getOdds(eventChild, String.valueOf(1), "|Double Chance|");
    }

    public String getOdds(EventChild eventChild, String outcomeCode, String marketName) {
        return getMarketChild(eventChild, marketName).market.children
                .stream()
                .filter(outcomeChild ->
                        outcomeChild.outcome.outcomeMeaningMinorCode.equals(outcomeCode))
                .map(outcomeChild -> outcomeChild.outcome.children.get(0).price.priceDec)
                .collect(Collectors.joining());
    }
}
