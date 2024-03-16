
package com.chrisp1985.footballodds.broker.ladbrokes.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Price {

    public String id;
    public String priceType;
    public String priceNum;
    public String priceDen;
    public String priceDec;
    public String handicapValueDec;
    public String rawHandicapValue;
    public String isActive;
    public String displayOrder;

}
