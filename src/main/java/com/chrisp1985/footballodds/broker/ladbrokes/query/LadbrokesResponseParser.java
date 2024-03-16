package com.chrisp1985.footballodds.broker.ladbrokes.query;

import com.chrisp1985.footballodds.broker.ladbrokes.model.enums.OutcomeMeaningMinorCode;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.EventChild;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.MarketChild;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.ResponseModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LadbrokesResponseParser {

    public Double returnPriceForMeaningCode(OutcomeMeaningMinorCode code, MarketChild event) {

        return Double.valueOf(event.market.children
                .stream()
                .filter(child -> child.outcome.outcomeMeaningMinorCode.equals(code.name()))
                .map(child -> child.outcome.children.get(0).price.priceDec).toList().get(0));

    }

    public MarketChild getMarketChild(EventChild event) {

        return event.event.children.stream().filter(marketChild -> marketChild.market.name.equals("|Match Result|")).toList().get(0);

    }

    public List<EventChild> getAvailableEvents(ResponseModel response) {

        return getEventChildrenFromResponse(response);

    }

    public List<EventChild> getEventChildrenFromResponse(ResponseModel response) {
        List<EventChild> emptyList = new ArrayList<>();
        return response.SSResponse.children.isEmpty() ? emptyList:
                response.SSResponse.children
                        .stream()
                        .filter(child -> Objects.nonNull(child.event))
                        .toList();
    }

    public String getOdds(EventChild eventChild, OutcomeMeaningMinorCode code) {
        return getMarketChild(eventChild).market.children
                .stream()
                .filter(outcomeChild ->
                        outcomeChild.outcome.outcomeMeaningMinorCode.equals(code.name()))
                .map(outcomeChild -> outcomeChild.outcome.children.get(0).price.priceDec)
                .collect(Collectors.joining());
    }
}
