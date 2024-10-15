package edu.coderhouse.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "INVOICE_DETAILS")
@Getter
@Setter
public class InvoiceDetail {

    public InvoiceDetail() {}

    public InvoiceDetail(Invoice invoice, Product product, int amount, double price) {
        this.invoice = invoice;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "PRICE")
    private double price;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
