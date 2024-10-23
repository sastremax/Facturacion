package edu.coderhouse.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "INVOICE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @Column(name = "id")
    @Schema(description = "Unique ID of the invoice", requiredMode = Schema.RequiredMode.AUTO, example = "0124529f-81b7-4924-952e-8d3fe108ab8f")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties("invoices")
    @Schema(description = "Client related to the invoice", requiredMode = Schema.RequiredMode.REQUIRED)
    private Client client;

    @Column(name = "created_at", nullable = false)
    @Schema(description = "Date when the invoice was created", example = "2023-08-08")
    private LocalDateTime createdAt;

    @Column(name = "total", nullable = false)
    @Schema(description = "Total amount of the invoice", requiredMode = Schema.RequiredMode.REQUIRED, example = "158754.10")
    private double total;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("invoice")
    @Schema(description = "List of details associated with the invoice")
    private List<InvoiceDetail> details;

    public Invoice(Client client, LocalDateTime createdAt, double total) {
        this.id = UUID.randomUUID();
        this.client = client;
        this.createdAt = createdAt;
        this.total = total;
        this.details = new ArrayList<>();
    }

}
