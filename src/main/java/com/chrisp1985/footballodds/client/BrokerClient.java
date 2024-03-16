package com.chrisp1985.footballodds.client;

import org.springframework.stereotype.Component;

@Component
public interface BrokerClient<T> {

    T queryOddsEndpoint(String path);

    T getOddsData();

}
