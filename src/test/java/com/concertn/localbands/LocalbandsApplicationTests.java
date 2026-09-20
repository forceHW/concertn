package com.concertn.localbands;

import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import com.concertn.localbands.services.NearbyPlacesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LocalbandsApplicationTests {

	@Autowired
	private NearbyPlacesService nearbyPlacesService;

//	@Test
//	void contextLoads() {
//	}


	@Test
	void hitRealApi() {
		List<NearbySearchResponse.Place> response = nearbyPlacesService.findNearbyVenues(39.298163, -76.600091,35000);

		System.out.println(response); // eyeball the payload
		assertNotNull(response);
	}
}
