
package com.chrisp1985.footballodds.broker.ladbrokes.model.response;

import com.chrisp1985.footballodds.model.response.BrokerResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel implements BrokerResponse {

    public SSResponse SSResponse;

}
