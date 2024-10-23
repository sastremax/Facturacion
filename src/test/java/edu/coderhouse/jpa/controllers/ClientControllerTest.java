package edu.coderhouse.jpa.controllers;
import edu.coderhouse.jpa.entities.Client;
import edu.coderhouse.jpa.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    private Client client;
    private UUID clientId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientId = UUID.randomUUID();
        client = new Client();
        client.setName("Fernando");
        client.setLastName("Gorchs");
        client.setDocNumber("25445269");
    }

    @Test
    public void testUpdateClient_Success() {
        when(clientService.updateClient(clientId, client)).thenReturn(client);

        ResponseEntity<?> response = clientController.updateClient(clientId, client);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateClient_ClientNotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"))
                .when(clientService).updateClient(clientId, client);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clientController.updateClient(clientId, client);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
