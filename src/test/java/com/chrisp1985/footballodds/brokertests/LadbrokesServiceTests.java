package com.chrisp1985.footballodds.brokertests;

import com.chrisp1985.footballodds.broker.ladbrokes.client.LadbrokesClient;
import com.chrisp1985.footballodds.broker.ladbrokes.conversion.LadbrokesEventToDynamo;
import com.chrisp1985.footballodds.broker.ladbrokes.model.enums.OutcomeMeaningMinorCode;
import com.chrisp1985.footballodds.broker.ladbrokes.model.response.EventChild;
import com.chrisp1985.footballodds.broker.ladbrokes.query.LadbrokesResponseParser;
import com.chrisp1985.footballodds.broker.ladbrokes.query.LadbrokesService;
import com.chrisp1985.footballodds.service.storage.StoreOddsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class LadbrokesServiceTests {

    @Mock
    private LadbrokesClient mockBrokerClient;

    @Mock
    private StoreOddsService mockStoreOddsService;

    @Mock
    private LadbrokesEventToDynamo mockLadbrokesEventToDynamo;

    @Mock
    private LadbrokesResponseParser mockLadbrokesResponseParser;

    private LadbrokesService ladbrokesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ladbrokesService = new LadbrokesService(mockBrokerClient, mockStoreOddsService, mockLadbrokesEventToDynamo, mockLadbrokesResponseParser);
    }

    @Test
    void LadbrokesResponseParser_areOddsGoodValue_lowHomeOdds_False() {
        EventChild mockEvent = mock(EventChild.class);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.H), any())).thenReturn(1.2);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.D), any())).thenReturn(3.2);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.A), any())).thenReturn(1.5);

        boolean result = ladbrokesService.areOddsGoodValue(mockEvent);

        assertFalse(result);
        verify(mockLadbrokesResponseParser, times(3)).returnPriceForMeaningCode(any(), any());
    }

    @Test
    void LadbrokesResponseParser_areOddsGoodValue_highAwayOdds_False() {
        EventChild mockEvent = mock(EventChild.class);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.H), any())).thenReturn(4.8);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.D), any())).thenReturn(3.2);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.A), any())).thenReturn(2.5);

        boolean result = ladbrokesService.areOddsGoodValue(mockEvent);

        assertFalse(result);
        verify(mockLadbrokesResponseParser, times(3)).returnPriceForMeaningCode(any(), any());
    }

    @Test
    void LadbrokesResponseParser_areOddsGoodValue_happypath_True() {
        EventChild mockEvent = mock(EventChild.class);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.H), any())).thenReturn(4.8);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.D), any())).thenReturn(3.2);
        when(mockLadbrokesResponseParser.returnPriceForMeaningCode(eq(OutcomeMeaningMinorCode.A), any())).thenReturn(1.5);

        boolean result = ladbrokesService.areOddsGoodValue(mockEvent);

        assertTrue(result);
        verify(mockLadbrokesResponseParser, times(3)).returnPriceForMeaningCode(any(), any());
    }

}
