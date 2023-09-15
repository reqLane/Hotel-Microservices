package com.naukma.reservationservice;

import com.naukma.reservationservice.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends CrudRepository<Reservation, Integer> {

}
