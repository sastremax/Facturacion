package edu.coderhouse.jpa.services;

import edu.coderhouse.jpa.dto.TimeApiResponse;
import edu.coderhouse.jpa.entities.Client;
import edu.coderhouse.jpa.entities.Invoice;
import edu.coderhouse.jpa.entities.InvoiceDetail;
import edu.coderhouse.jpa.entities.Product;
import edu.coderhouse.jpa.exceptions.InsufficientStockException;
import edu.coderhouse.jpa.repositories.InvoiceRepository;
import edu.coderhouse.jpa.repositories.ClientRepository;
import edu.coderhouse.jpa.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public Invoice createInvoice(Invoice invoice) throws InsufficientStockException {
        log.debug("Validating invoice...");
        validateInvoice(invoice);

        log.debug("Fetching current date...");
        LocalDate currentDate = getCurrentDate();
        invoice.setCreatedAt(currentDate);

        log.debug("Calculating total for the invoice...");
        double total = calculateTotal(invoice);
        invoice.setTotal(total);

        log.debug("Saving invoice to the database...");
        return invoiceRepository.save(invoice);
    }

    private void validateInvoice(Invoice invoice) {

        Optional<Client> client = clientRepository.findById(invoice.getClient().getId());
        if (client.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente no existe");
        }

        for (InvoiceDetail detail : invoice.getDetails()) {
            Optional<Product> product = productRepository.findById(detail.getProduct().getId());
            if (product.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto no existe");
            }

            if (detail.getAmount() > product.get().getStock()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad mayor al stock disponible");
            }

            product.get().setStock(product.get().getStock() - detail.getAmount());
            productRepository.save(product.get());
        }
    }

    private double calculateTotal(Invoice invoice) {
        double total = 0;
        for (InvoiceDetail detail : invoice.getDetails()) {
            total += detail.getAmount() * detail.getProduct().getPrice();
        }
        return total;
    }

    public LocalDate getCurrentDate() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = "https://timeapi.io/api/Time/current/zone?timeZone=UTC";
            TimeApiResponse response = restTemplate.getForObject(url, TimeApiResponse.class);
            if (response != null && response.getDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                return LocalDate.parse(response.getDate(), formatter);
            }
        } catch (Exception e) {
            log.error("Error fetching current date from API, using system date instead: {}", e.getMessage());
        }
        return LocalDate.now();
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(String id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public Invoice updateInvoice(String id, Invoice invoiceDetails) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isPresent()) {
            Invoice invoice = invoiceOptional.get();
            invoice.setDetails(invoiceDetails.getDetails());
            invoice.setClient(invoiceDetails.getClient());
            invoice.setTotal(calculateTotal(invoice));
            return invoiceRepository.save(invoice);
        } else {
            return null;
        }
    }

    public boolean deleteInvoice(String id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isPresent()) {
            invoiceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
