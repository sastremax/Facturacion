package edu.coderhouse.jpa.services;

import edu.coderhouse.jpa.dto.TimeApiResponse;
import edu.coderhouse.jpa.entities.Client;
import edu.coderhouse.jpa.entities.Invoice;
import edu.coderhouse.jpa.entities.InvoiceDetail;
import edu.coderhouse.jpa.entities.Product;
import edu.coderhouse.jpa.repositories.InvoiceRepository;
import edu.coderhouse.jpa.repositories.ClientRepository;
import edu.coderhouse.jpa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public Invoice createInvoice(Invoice invoice) {
        validateInvoice(invoice);

        double total = calculateTotal(invoice);
        invoice.setTotal(total);

        invoice.setCreatedAt(getCurrentDate());

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

    private LocalDateTime getCurrentDate() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = "http://worldclockapi.com/api/json/utc/now";
            TimeApiResponse response = restTemplate.getForObject(url, TimeApiResponse.class);
            return LocalDateTime.parse(response.getCurrentDateTime());
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }

}
