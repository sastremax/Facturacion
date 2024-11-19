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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client[].class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok(this.service.getClients());
    }

    @Operation(summary = "Obtener un cliente por ID", description = "Obtiene los datos de un cliente específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable String id) {
        Client client = service.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Buscar cliente por número de documento", description = "Obtiene los datos de un cliente utilizando su número de documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    @GetMapping("/doc/{docNumber}")
    public ResponseEntity<Client> getClientByDocNumber(@PathVariable String docNumber) {
        Client client = service.findClientByDocNumber(docNumber);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "Crear un nuevo cliente", description = "Crea un cliente y lo devuelve con su ID generado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "400", description = "Número de documento duplicado o solicitud inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createClient(
            @RequestBody
            @io.swagger.v3.oas.annotations.media.Schema(example = "{\n" +
                    "  \"name\": \"Maxi\",\n" +
                    "  \"lastName\": \"Sastre\",\n" +
                    "  \"docNumber\": \"12345678\"\n" +
                    "}")
            Client client) {
        Client savedClient = this.service.createClient(client);
        return ResponseEntity.status(201).body(savedClient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente existente por ID", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    public ResponseEntity<?> updateClient(@PathVariable String id, @RequestBody Client client) {
        service.updateClient(id, client);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente por ID", description = "Elimina un cliente del sistema utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))
                    })
    })
    public ResponseEntity<?> deleteClient(@PathVariable String id) {
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
