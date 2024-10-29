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

import java.time.LocalDate;
import java.util.List;

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
        if (invoice.getClient() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "El cliente es obligatorio",
                            "client"));
        }

        if (invoice.getDetails() == null || invoice.getDetails().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "Los detalles de la factura son obligatorios",
                            "details"));
        }

        for (var detail : invoice.getDetails()) {
            if (detail.getProduct() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Cada detalle debe tener un producto asociado",
                                "product"));
            }
            if (detail.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "La cantidad del producto debe ser mayor a 0",
                                "amount"));
            }
        }

        try {
            LocalDate currentDate = invoiceService.getCurrentDate();

            invoice.setCreatedAt(currentDate);

            Invoice createdInvoice = invoiceService.createInvoice(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);

        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.CONFLICT.value()),
                            HttpStatus.CONFLICT.getReasonPhrase(),
                            e.getMessage(),
                            "stock"));

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            "Se produjo un error al procesar los datos de la factura. Verifica los campos requeridos.",
                            "invoice"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            "Error inesperado del servidor. Inténtalo más tarde.",
                            "internal_error"));
        }
    }

    @Operation(summary = "Obtener todas las facturas")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Facturas obtenidas exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron las facturas",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @GetMapping
    public ResponseEntity<?> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        if (invoices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.NOT_FOUND.value()),
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            "No se encontraron todas las facturas",
                            "invoices"));
        }
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Obtener una factura por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable String id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase(), "Factura no encontrada", "id"));
        }
        return ResponseEntity.ok(invoice);
    }

    @Operation(summary = "Actualizar una factura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable String id, @RequestBody Invoice invoiceDetails) {
        Invoice updatedInvoice = invoiceService.updateInvoice(id, invoiceDetails);
        if (updatedInvoice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase(), "Factura no encontrada", "id"));
        }
        return ResponseEntity.ok(updatedInvoice);
    }

    @Operation(summary = "Eliminar una factura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Factura eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable String id) {
        boolean deleted = invoiceService.deleteInvoice(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDto(String.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase(), "Factura no encontrada", "id"));
        }
        return ResponseEntity.noContent().build();
    }

}
