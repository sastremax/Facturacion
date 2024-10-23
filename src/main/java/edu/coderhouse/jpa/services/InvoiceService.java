package edu.coderhouse.jpa.services;

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
import org.springframework.web.server.ResponseStatusException;

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

}
