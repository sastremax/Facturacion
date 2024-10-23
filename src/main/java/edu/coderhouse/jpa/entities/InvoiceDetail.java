package edu.coderhouse.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "INVOICE_DETAILS")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceDetail {

    public InvoiceDetail(Invoice invoice, Product product, int amount, double price) {
        this.invoice = invoice;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @Schema(description = "Unique ID of the invoice detail", requiredMode = Schema.RequiredMode.AUTO, example = "11223344-81b7-4924-952e-8d3fe108ab8f")
    private UUID id;


    @Column(name = "AMOUNT", nullable = false)
    @Schema(description = "Amount of products in this detail", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private int amount;

    @Column(name = "PRICE", nullable = false)
    @Schema(description = "Price of the product at the time of the invoice", requiredMode = Schema.RequiredMode.REQUIRED, example = "750.25")
    private double price;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonIgnoreProperties("details")
    @Schema(description = "Invoice associated with this detail", requiredMode = Schema.RequiredMode.REQUIRED)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties("details")
    @Schema(description = "Product associated with this detail", requiredMode = Schema.RequiredMode.REQUIRED)
    private Product product;

    public double getTotal() {
        return amount * price;
    }

}
