package edu.coderhouse.jpa.repositories;

import edu.coderhouse.jpa.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {}