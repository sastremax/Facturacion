package edu.coderhouse.jpa.services;

import edu.coderhouse.jpa.entities.Client;
import edu.coderhouse.jpa.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void updateClient(UUID id, Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
        existingClient.setName(client.getName());
        existingClient.setLastName(client.getLastName());
        existingClient.setDocNumber(client.getDocNumber());
        clientRepository.save(existingClient);
    }

    public void deleteClient(UUID id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
    }

    public Client findClientByDocNumber(String docNumber) {
        return clientRepository.findByDocNumber(docNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

}
