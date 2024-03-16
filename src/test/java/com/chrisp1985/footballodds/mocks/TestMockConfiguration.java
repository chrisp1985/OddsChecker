package com.chrisp1985.footballodds.mocks;

import com.chrisp1985.footballodds.broker.ladbrokes.client.LadbrokesClient;
import com.chrisp1985.footballodds.service.storage.StoreOddsService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestMockConfiguration {

    @Bean
    @Primary
    public LadbrokesClient ladbrokesClient() {
        return Mockito.mock(LadbrokesClient.class);
    }

    @Bean
    public StoreOddsService storeOddsService() {
        return Mockito.mock(StoreOddsService.class);
    }
}
