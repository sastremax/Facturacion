package edu.coderhouse.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name= "CLIENT")
@Getter
@Setter
public class Client {

    public Client() {}

    public Client(String name, String lastname, String docnumber) {
        this.name = name;
        this.lastname = lastname;
        this.docnumber = docnumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "DOCNUMBER")
    private String docnumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;

}
