
package com.chrisp1985.footballodds.broker.ladbrokes.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class EventChild {

    public Event event;

}
