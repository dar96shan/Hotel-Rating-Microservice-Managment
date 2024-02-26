package com.micro.HotelService.service;

import com.micro.HotelService.entity.Hotel;
import com.micro.HotelService.exception.ResourceNotFoundException;
import com.micro.HotelService.repository.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService{
    @Autowired
    HotelRepo hotelRepo;
    @Override
    public Hotel createHotel(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return this.hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return this.hotelRepo.findAll();

    }

    @Override
    public Hotel getByHotelId(String hotelId) {
        Hotel hotelIdNotFound = this.hotelRepo.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with given Id is not found"));
        return hotelIdNotFound;
    }
}
