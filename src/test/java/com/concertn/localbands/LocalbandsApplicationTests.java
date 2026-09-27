package com.concertn.localbands;

import com.concertn.localbands.domain.dtos.AIEventResponseDto;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import com.concertn.localbands.domain.entities.Event;
import com.concertn.localbands.services.AiParseService;
import com.concertn.localbands.services.EventsService;
import com.concertn.localbands.services.NearbyPlacesService;
import com.concertn.localbands.services.impl.EventsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LocalbandsApplicationTests {

	@Autowired
	private NearbyPlacesService nearbyPlacesService;

	@Autowired
	private AiParseService aiParseService;

	@Autowired
	private EventsService eventsService;

//	@Test
//	void contextLoads() {
//	}


	@Test
	void hitRealApi() {
		List<NearbySearchResponse.Place> response = nearbyPlacesService.findNearbyVenues(39.298163, -76.600091,35000);

		System.out.println(response); // eyeball the payload
		assertNotNull(response);
	}


	@Test
	void googleAndLuna(){
//		used to test the ai
		List<NearbySearchResponse.Place> places = nearbyPlacesService.findNearbyVenues(39.298163, -76.600091,35000);

		NearbySearchResponse.Place argAi = places.get(1);

		List<AIEventResponseDto> resp = aiParseService.ParseByPlace(argAi);
		System.out.println("GP + Luna " + resp);
		assertNotNull(resp);
	}


	@Test
	void testEventsServiceFetchEvents(){
		Page<Event> resp = eventsService.fetchEventsByLocation(39.298163, -76.600091, 35000);

		assertNotNull(resp);
	}
}
