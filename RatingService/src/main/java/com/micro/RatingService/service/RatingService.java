package com.micro.RatingService.service;

import com.micro.RatingService.entity.Rating;

import java.util.List;

public interface RatingService {

    //Create
    Rating Create(Rating rating);

    //Get all ratings
    List<Rating> getAllRatings();

    //get all by userId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);
}
