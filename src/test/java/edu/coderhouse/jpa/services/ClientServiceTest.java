package edu.coderhouse.jpa.services;

import edu.coderhouse.jpa.entities.Client;
import edu.coderhouse.jpa.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;
    private UUID clientId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientId = UUID.randomUUID();
        client = new Client();
        client.setId(clientId);
        client.setName("Fernando");
        client.setLastName("Gorchs");
        client.setDocNumber("25445269");
    }

    @Test
    public void testUpdateClient_Success() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        clientService.updateClient(clientId, client);
        // Verificación adicional si es necesaria
    }

    @Test
    public void testUpdateClient_ClientNotFound() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clientService.updateClient(clientId, client);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
