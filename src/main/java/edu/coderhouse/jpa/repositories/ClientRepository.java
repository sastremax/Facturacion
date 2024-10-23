package edu.coderhouse.jpa.repositories;

import edu.coderhouse.jpa.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByDocNumber(String docNumber);
}