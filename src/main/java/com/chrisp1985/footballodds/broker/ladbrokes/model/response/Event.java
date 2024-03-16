
package com.chrisp1985.footballodds.broker.ladbrokes.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class Event {

    public String id;
    public String name;
    public String eventStatusCode;
    public String isActive;
    public String isDisplayed;
    public String displayOrder;
    public String siteChannels;
    public String eventSortCode;
    public String startTime;
    public String rawIsOffCode;
    public String classId;
    public String typeId;
    public String sportId;
    public String liveServChannels;
    public String liveServChildrenChannels;
    public String categoryId;
    public String categoryCode;
    public String categoryName;
    public String categoryDisplayOrder;
    public String className;
    public String classDisplayOrder;
    public String classSortCode;
    public String typeName;
    public String typeDisplayOrder;
    public String typeFlagCodes;
    public String isOpenEvent;
    public String isNext6HourEvent;
    public String isNext12HourEvent;
    public String isNext24HourEvent;
    public String isNext2DayEvent;
    public String isNext1WeekEvent;
    public String isLiveNowOrFutureEvent;
    public String drilldownTagNames;
    public String isAvailable;
    public String cashoutAvail;
    public List<MarketChild> children;

}
