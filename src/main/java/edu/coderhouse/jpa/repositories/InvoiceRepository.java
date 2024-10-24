package edu.coderhouse.jpa.repositories;

import edu.coderhouse.jpa.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}