package com.chrisp1985.footballodds.broker.ladbrokes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class LadbrokesRestTemplateConfiguration {

    @Bean(name = "ladbrokesRestClient")
    public RestClient ladbrokesRestClient(
            @Value("${application.urls.ladbrokes.base}") String ladbrokesBaseUrl,
            @Value("${application.urls.ladbrokes.authority}") String authority,
            @Value("${application.urls.ladbrokes.origin}") String origin,
            @Value("${application.urls.ladbrokes.referrer}") String referrer
    ) {
        return RestClient.builder()
                .baseUrl(ladbrokesBaseUrl)
                .defaultHeader("authority", authority)
                .defaultHeader("method", "GET")
                .defaultHeader("scheme", "https")
                .defaultHeader("origin", origin)
                .defaultHeader("referrer", referrer)
                .build();
    }
}
