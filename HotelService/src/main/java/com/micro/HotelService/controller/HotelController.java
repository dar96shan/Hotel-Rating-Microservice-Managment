package com.micro.HotelService.controller;

import com.micro.HotelService.entity.Hotel;
import com.micro.HotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    //create
    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping()
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        Hotel create = hotelService.createHotel(hotel);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    //get single
    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/{hotelId}")
    public  ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId){
        Hotel byHotelId = hotelService.getByHotelId(hotelId);
        return new ResponseEntity<>(byHotelId,HttpStatus.OK);
    }

    //get all
    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("/all")
    public ResponseEntity<List<Hotel>> getAllHotels(){
        //List<Hotel> all = hotelService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
    }

}
