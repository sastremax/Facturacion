package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.dto.ErrorResponseDto;
import edu.coderhouse.jpa.entities.Client;
import edu.coderhouse.jpa.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    @Autowired
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todos los clientes", description = "Obtiene una lista de todos los clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesful operation"),
            @ApiResponse(responseCode = "400", description = "invalid request"),
    })
    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok(this.service.getClients());
    }

    @Operation(summary = "Obtener un cliente por ID", description = "Obtiene los datos de un cliente específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) }
            )})
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable UUID id) {
        Client client = service.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Buscar cliente por número de documento", description = "Obtiene los datos de un cliente utilizando su número de documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) }
            )})
    @GetMapping("/doc/{docNumber}")
    public ResponseEntity<Client> getClientByDocNumber(@PathVariable String docNumber) {
        Client client = service.findClientByDocNumber(docNumber);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesful operation"),
            @ApiResponse(responseCode = "400", description = "invalid parameters",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) }
            )})
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = this.service.createClient(client);
        return ResponseEntity.ok(savedClient);
    }

    @Operation(summary = "Actualizar un cliente existente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesful update Client"),
            @ApiResponse(responseCode = "404", description = "Client no found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) }
            )})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable UUID id, @RequestBody Client client) {
        try {
            service.updateClient(id, client);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto("404", "Not Found", "Cliente no encontrado", "id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Operation(summary = "Eliminar un cliente por ID", description = "Elimina un cliente del sistema utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Succesful delete Client"),
            @ApiResponse(responseCode = "404", description = "Client no found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) }
            )})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable UUID id) {
        try {
            service.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            ErrorResponseDto errorResponse = new ErrorResponseDto("404", "Not Found", "Cliente no encontrado", "id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
