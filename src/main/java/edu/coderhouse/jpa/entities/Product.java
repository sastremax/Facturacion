package edu.coderhouse.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
public class Product {

    public Product() {}

    public Product(String description, String codigo, int stock, double price) {
        this.description = description;
        this.codigo = codigo;
        this.stock = stock;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "STOCK")
    private int stock;

    @Column(name = "PRICE")
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceDetail> details;

}
