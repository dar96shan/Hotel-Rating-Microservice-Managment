package com.micro.RatingService.controllers;

import com.micro.RatingService.entity.Rating;
import com.micro.RatingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    //create rating
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.Create(rating));
    }

    //Get all Rating
    @GetMapping("/all")
    public ResponseEntity<List<Rating>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRatings());
    }

    //Get all by UserId
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getAllByUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.getRatingByUserId(userId));
    }
    //Get all by UserId
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getAllByHotelId(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.getRatingByHotelId(hotelId));
    }
}
