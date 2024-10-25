package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.dto.ErrorResponseDto;
import edu.coderhouse.jpa.entities.Invoice;
import edu.coderhouse.jpa.services.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Operation(summary = "Crear un nuevo comprobante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comprobante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (datos incompletos o incorrectos)",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) }),

            @ApiResponse(responseCode = "409", description = "El cliente o producto no existen o no hay suficiente stock",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        try {
            if (invoice.getClient() == null || invoice.getDetails() == null || invoice.getDetails().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDto("La solicitud es inválida. Asegúrate de incluir cliente y detalles."));
            }
            Invoice createdInvoice = invoiceService.createInvoice(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDto(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto("Error inesperado del servidor. Inténtalo más tarde."));
        }

    }
}
