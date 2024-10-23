package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.entities.Invoice;
import edu.coderhouse.jpa.services.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
public class InvoiceControllerTest {

    @InjectMocks
    private InvoiceController invoiceController;

    @Mock
    private InvoiceService invoiceService;

    private Invoice invoice;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        invoice = new Invoice();
    }

    @Test
    public void testCreateInvoice_Success() {
        when(invoiceService.createInvoice(invoice)).thenReturn(invoice);
        ResponseEntity<Invoice> response = invoiceController.createInvoice(invoice);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
