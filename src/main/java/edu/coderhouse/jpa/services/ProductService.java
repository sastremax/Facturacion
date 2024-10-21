package edu.coderhouse.jpa.services;
import edu.coderhouse.jpa.entities.Product;
import edu.coderhouse.jpa.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error to save the product", e);
        }
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(String id, Product newProduct) {
        return productRepository.findById(id).map(product -> {
            product.setDescription(newProduct.getDescription());
            product.setCodigo(newProduct.getCodigo());
            product.setStock(newProduct.getStock());
            product.setPrice(newProduct.getPrice());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
