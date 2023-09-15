package com.naukma.clientservice;

import com.naukma.clientservice.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createClient(@RequestBody Client data) {
        try {
            clientService.register(data);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Boolean> updateClient(@RequestBody Map<String, String> data) {
        try {
            Client client = clientService.findByEmail(data.get("oldEmail"));

            client.setName(data.get("newName"));
            client.setSurname(data.get("newSurname"));
            client.setCountry(data.get("newCountry"));
            client.setEmail(data.get("newEmail"));
            client.setPassword(data.get("newPassword"));

            clientService.update(client);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> data) {
        try {
            String email = data.get("email");
            String password = data.get("password");
            Map<String, String> response = clientService.authenticate(email, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("authenticated", "false");
            response.put("role", null);
            response.put("exception", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/emailAlreadyOccupied")
    public ResponseEntity<Boolean> emailAlreadyOccupied(@RequestBody Map<String, String> data) {
        try {
            boolean response = clientService.emailAlreadyOccupied(data.get("email"));
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getByEmail/{clientEmail}")
    public ResponseEntity<Map<String, Object>> getClientByEmail(@PathVariable String clientEmail) {
        try {
            Client client = clientService.findByEmail(clientEmail);

            return ResponseEntity.ok(client.toMap());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
