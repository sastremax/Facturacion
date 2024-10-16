package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.entities.Product;
import edu.coderhouse.jpa.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = service.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        Optional<Product> product = service.getById(id);

        if (product.isPresent()) {
            // Si el producto existe, devolver 200 OK con el producto
            return ResponseEntity.ok(product.get());
        } else {
            // Si el producto no existe, devolver 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            Product newProduct = service.save(product);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
