package edu.coderhouse.jpa.services;
import edu.coderhouse.jpa.entities.Product;
import edu.coderhouse.jpa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product) {
        try {
            return repository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el producto", e);
        }
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Optional<Product> getById(Integer id) {
        return repository.findById(id);
    }

    public Product updateProduct(Integer id, Product newProduct) {
        return repository.findById(id).map(product -> {
            product.setDescription(newProduct.getDescription());
            product.setCodigo(newProduct.getCodigo());
            product.setStock(newProduct.getStock());
            product.setPrice(newProduct.getPrice());
            return repository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void deleteProduct(Integer id) {
        repository.deleteById(id);
    }

    public Product getProductById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

}
