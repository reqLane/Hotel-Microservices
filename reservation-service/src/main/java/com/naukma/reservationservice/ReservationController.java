package com.naukma.reservationservice;

import com.naukma.reservationservice.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createReservation(@RequestBody Map<String, String> data) {
        try {
            Reservation reservation = new Reservation();

            reservation.setAdults(Integer.parseInt(data.get("adults")));
            reservation.setReservationPrice(new BigDecimal(data.get("reservationPrice")));
            reservation.setCheckIn(Date.valueOf(data.get("checkIn")));
            reservation.setCheckOut(Date.valueOf(data.get("checkOut")));

            String clientEmail = data.get("clientEmail");

            String hotelAddress = data.get("hotelAddress");
            Integer roomNumber = Integer.parseInt(data.get("roomNumber"));

            reservation = reservationService.create(reservation, clientEmail, hotelAddress, roomNumber);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Boolean> deleteReservation(@RequestBody Map<String, String> data) {
        try {
            reservationService.deleteById(Integer.parseInt(data.get("reservationId")));
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/byClientEmail")
    public ResponseEntity<List<Map<String, Object>>> getAllByClientEmail(@RequestBody Map<String, String> data) {
        try {
            List<Map<String, Object>> response = new ArrayList<>();

            String clientEmail = data.get("clientEmail");

            for (Reservation reservation : reservationService.findByClientEmail(clientEmail)) {
                response.add(reservation.toMap());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/byHotelAndNumber")
    public ResponseEntity<List<Map<String, Object>>> getAllByHotelAndNumber(@RequestBody Map<String, String> data) {
        try {
            List<Map<String, Object>> response = new ArrayList<>();

            String hotelAddress = data.get("hotelAddress");
            Integer roomNumber = Integer.parseInt(data.get("roomNumber"));

            for (Reservation reservation : reservationService.findByHotelAndNumber(hotelAddress, roomNumber)) {
                response.add(reservation.toMap());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
