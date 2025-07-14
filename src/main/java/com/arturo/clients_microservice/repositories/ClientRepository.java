package com.arturo.clients_microservice.repositories;

import com.arturo.clients_microservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
