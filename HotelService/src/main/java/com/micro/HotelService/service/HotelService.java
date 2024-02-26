package com.micro.HotelService.service;

import com.micro.HotelService.entity.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel createHotel(Hotel hotel);

    //getAll
    List<Hotel> getAll();

    //get Single
    Hotel getByHotelId(String HotelId);
}
