
package com.chrisp1985.footballodds.broker.ladbrokes.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class Market {

    public String id;
    public String eventId;
    public String templateMarketId;
    public String templateMarketName;
    public String dispSortId;
    public String dispSortName;
    public String collectionIds;
    public String collectionNames;
    public String marketMeaningMajorCode;
    public String marketMeaningMinorCode;
    public String name;
    public String isLpAvailable;
    public String rawHandicapValue;
    public String isMarketBetInRun;
    public String displayOrder;
    public String marketStatusCode;
    public String isActive;
    public String isDisplayed;
    public String siteChannels;
    public String liveServChannels;
    public String liveServChildrenChannels;
    public String priceTypeCodes;
    public String isAvailable;
    public String maxAccumulators;
    public String minAccumulators;
    public String cashoutAvail;
    public String termsWithBet;
    public List<OutcomeChild> children;

}
