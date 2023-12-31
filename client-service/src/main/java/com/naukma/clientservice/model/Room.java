package com.naukma.clientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Positive
    private Integer number;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    private Integer capacity;

    @NotNull
    @Lob
    private byte[] image;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hotel_fk")
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservationList;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("number", number);
        map.put("price", price);
        map.put("capacity", capacity);
        map.put("image", image);
        return map;
    }
}
