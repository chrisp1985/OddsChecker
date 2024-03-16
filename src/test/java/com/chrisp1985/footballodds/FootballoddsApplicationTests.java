package com.chrisp1985.footballodds;

import com.chrisp1985.footballodds.broker.ladbrokes.client.LadbrokesClient;
import com.chrisp1985.footballodds.service.storage.StoreOddsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FootballoddsApplicationTests {

	@Autowired
	LadbrokesClient ladbrokesClient;

	@Autowired
	StoreOddsService storeOddsService;

	@Test
	void contextLoads() {
//		ResponseModel responseModel = ResponseModel.builder().SSResponse(SSResponse.builder().build()).build();
//
//		List<EventChild> eventChildList = new OddsQueryService(ladbrokesClient, storeOddsService).getAvailableEvents(responseModel);
//
//		Assertions.assertTrue(eventChildList.isEmpty());
	}

}
