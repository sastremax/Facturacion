package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.dto.ErrorResponseDto;
import edu.coderhouse.jpa.entities.Invoice;
import edu.coderhouse.jpa.exceptions.InsufficientStockException;
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

    @Operation(summary = "Crear una nueva factura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Factura creada exitosamente"),
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
                        .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase(), "La solicitud es inválida. Asegúrate de incluir cliente y detalles.", "client, details"));
            }
            Invoice createdInvoice = invoiceService.createInvoice(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);

        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), "stock"));

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "Se produjo un error al procesar los datos de la factura. Verifica los campos requeridos.", "invoice"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Error inesperado del servidor. Inténtalo más tarde.", "internal_error"));
        }

    }

    @Operation(summary = "Obtener todas las facturas")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Facturas obtenidas exitosamente"),
            @ApiResponse(responsecode = "404", description = "No se encontraron las facturas"),
                content = { @content(mediatype = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) })
    })

}
