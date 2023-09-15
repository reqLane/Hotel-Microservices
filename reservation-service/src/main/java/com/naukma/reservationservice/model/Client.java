package com.naukma.reservationservice.model;

import com.naukma.reservationservice.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$")
    private String country;

    @NotNull
    @Column(unique = true)
    @Email
    private String email;

    @NotNull
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservationList;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("role", role);
        map.put("name", name);
        map.put("surname", surname);
        map.put("country", country);
        map.put("email", email);
        map.put("password", password);
        return map;
    }
}
