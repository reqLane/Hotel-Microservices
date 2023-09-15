package com.naukma.reservationservice;

import com.naukma.reservationservice.model.Client;
import com.naukma.reservationservice.model.Reservation;
import com.naukma.reservationservice.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private static final String CLIENTS_API = "http://localhost:8079/api/clients";
    private static final String ROOMS_API = "http://localhost:8080/api/rooms";

    private final ReservationRepo reservationRepo;

    private final RestTemplate restTemplate;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo, RestTemplate restTemplate) {
        this.reservationRepo = reservationRepo;
        this.restTemplate = restTemplate;
    }

    //OPERATIONS

    public List<Reservation> findByClientEmail(String clientEmail) {

        Client client = restTemplate.getForObject(CLIENTS_API + "/getByEmail/" + clientEmail, Client.class);

//        List<Reservation> result = new ArrayList<>(client.getReservationList());
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservationRepo.findAll()) {
            if(reservation.getClient().getId().equals(client.getId()))
                result.add(reservation);
        }

        result.sort(Comparator.comparing(Reservation::getCheckIn));

        return result;
    }

    public List<Reservation> findByHotelAndNumber(String hotelAddress, Integer roomNumber) {

        Room room = restTemplate.getForObject(ROOMS_API + "/getByHotelAndNumber/" + hotelAddress + "/" + roomNumber, Room.class);

//        List<Reservation> result = new ArrayList<>(room.getReservationList());
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservationRepo.findAll()) {
            if(reservation.getRoom().getId().equals(room.getId()))
                result.add(reservation);
        }

        result.sort(Comparator.comparing(Reservation::getCheckIn));

        return result;
    }

    //DEFAULT OPERATIONS

    public List<Reservation> findAll() {
        List<Reservation> result = new ArrayList<>();
        for (Reservation room : reservationRepo.findAll()) {
            result.add(room);
        }
        return result;
    }

    public Reservation findById(Integer id) {
        Optional<Reservation> result = reservationRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Reservation create(Reservation reservation, String clientEmail, String hotelAddress, Integer roomNumber) {
        Client client = restTemplate.getForObject(CLIENTS_API + "/getByEmail/" + clientEmail, Client.class);
        Room room = restTemplate.getForObject(ROOMS_API + "/getByHotelAndNumber/" + hotelAddress + "/" + roomNumber, Room.class);

        reservation.setClient(client);
        reservation.setRoom(room);

        return reservationRepo.save(reservation);
    }

    public void update(Reservation reservation) {
        reservationRepo.save(reservation);
    }

    public void deleteById(Integer id) {
        reservationRepo.deleteById(id);
    }

    public void delete(Reservation reservation) {
        reservationRepo.deleteById(reservation.getId());
    }

    public void deleteAll() {
        reservationRepo.deleteAll();
    }
}
