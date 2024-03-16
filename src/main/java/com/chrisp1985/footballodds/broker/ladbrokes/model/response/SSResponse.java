
package com.chrisp1985.footballodds.broker.ladbrokes.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SSResponse {

    public String xmlns;
    public List<EventChild> children;

}
