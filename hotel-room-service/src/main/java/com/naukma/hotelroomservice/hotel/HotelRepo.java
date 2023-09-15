package com.naukma.hotelroomservice.hotel;

import com.naukma.hotelroomservice.model.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepo extends CrudRepository<Hotel, Integer> {

    Hotel findFirstByAddressEquals(String address);
}
