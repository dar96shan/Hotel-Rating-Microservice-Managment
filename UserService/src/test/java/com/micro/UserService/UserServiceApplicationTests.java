package com.micro.UserService;

import com.micro.UserService.entity.Rating;
import com.micro.UserService.external.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private RatingService ratingService;

	@Test
	void contextLoads() {
	}

//	@Test
//	void createRating(){
//		Rating rating = Rating.builder()
//						.ratingId("Testing_Id")
//								.userId("Testing_userId")
//										.hotelId("testing_hotelId")
//												.rating(111)
//														.feedback("Good Feedback for testing")
//																.build();
//
//		Rating createRating = ratingService.createRating(rating);
//		System.out.println("Rating created :"+createRating);
//
//
//	}

}
