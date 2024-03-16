
package com.chrisp1985.footballodds.broker.ladbrokes.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class Outcome {

    public String id;
    public String marketId;
    public String name;
    public String outcomeMeaningMajorCode;
    public String outcomeMeaningMinorCode;
    public String displayOrder;
    public String outcomeStatusCode;
    public String isActive;
    public String isDisplayed;
    public String siteChannels;
    public String liveServChannels;
    public String liveServChildrenChannels;
    public String isAvailable;
    public List<PriceChild> children;

}
