package edu.coderhouse.jpa.services;
import edu.coderhouse.jpa.entities.Product;
import edu.coderhouse.jpa.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(@Valid Product product) {
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product data is not valid", e);
        } catch (Exception e) {
            throw new RuntimeException("Error to save the product", e);
        }
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(UUID id, Product newProduct) {
        return productRepository.findById(id).map(product -> {
            product.setDescription(newProduct.getDescription());
            product.setCodigo(newProduct.getCodigo());
            product.setStock(newProduct.getStock());
            product.setPrice(newProduct.getPrice());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
