package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.entities.Client;
import edu.coderhouse.jpa.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok(this.service.getClients());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        this.service.createClient(client);
        return ResponseEntity.ok(client);
    }

}
