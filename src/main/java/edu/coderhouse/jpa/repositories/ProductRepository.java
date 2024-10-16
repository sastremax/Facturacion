package edu.coderhouse.jpa.repositories;

import edu.coderhouse.jpa.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Integer> {
}