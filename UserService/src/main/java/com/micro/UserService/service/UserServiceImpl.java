package com.micro.UserService.service;

import com.micro.UserService.entity.Hotel;
import com.micro.UserService.entity.Rating;
import com.micro.UserService.entity.User;
import com.micro.UserService.exception.ResourceNotFoundException;
import com.micro.UserService.external.service.HotelService;
import com.micro.UserService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return this.userRepository.save(user);

    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUser = userRepository.findAll();

        allUser.forEach(user -> {
            // Fetch ratings for the user
            String url = "http://RATING-SERVICE/ratings/users/"+user.getUserId();
            ResponseEntity<List<Rating>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Rating>>() {
            });
            logger.info("Response body : {}",response.getBody());
            List<Rating> userRating = response.getBody();

            if(userRating != null){
                //For Each rating , fetch Hotel
                userRating.forEach(rating -> {
                    String hotelUrl = "http://HOTEL-SERVICE/hotels/" + rating.getHotelId();
                    ResponseEntity<Hotel> hotelResponse = restTemplate.exchange(hotelUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Hotel>() {
                    });

                    Hotel hotel = hotelResponse.getBody();
                    logger.info("Hotel body : {}",hotel);
                    rating.setHotel(hotel);

                    //I used Feign client to communicate between microservice

//                    Hotel hotelResponse = hotelService.getHotel(rating.getHotelId());
//                    rating.setHotel(hotelResponse);

                });
                user.setRatings(userRating);
            }
        });

        return allUser;
    }

    @Override
    public User getUser(String userId) {
        //get user from database with the help of User Repository
        User userById = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server..!! :" + userId));

        //Fetch rating of the above user from Rating Service
//        ArrayList<Rating> ratingOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+userById.getUserId(), ArrayList.class);
//
//        logger.info(" Rating : {} " ,ratingOfUser);
//        userById.setRatings(ratingOfUser);

        String url = "http://RATING-SERVICE/ratings/users/"+userById.getUserId();
        ResponseEntity<List<Rating>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Rating>>() {
        });

        List<Rating> ratingByUser = response.getBody();

        List<Rating> UserByRatingHotel = ratingByUser.stream()
                .map(rating -> {
//                    String url1 = "http://HOTEL-SERVICE/hotels/" + rating.getHotelId();
//                    ResponseEntity<Hotel> hotel = restTemplate.exchange(url1, HttpMethod.GET, null, new ParameterizedTypeReference<Hotel>() {
//                    });
//                    Hotel hotelRating = hotel.getBody();
//                    rating.setHotel(hotelRating);

                    Hotel hotelResponse = hotelService.getHotelById(rating.getHotelId());
                    rating.setHotel(hotelResponse);
                    return rating;
                })
                .collect(Collectors.toList());

        if (ratingByUser != null) {
            userById.setRatings(UserByRatingHotel);
        } else {
            // Log a warning message indicating the ratings are null
            logger.warn("Received null ratings for user with ID: {}", userById.getUserId());
        }


        return userById;
    }
}
