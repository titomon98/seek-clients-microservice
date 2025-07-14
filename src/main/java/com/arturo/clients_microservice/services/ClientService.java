package com.arturo.clients_microservice.services;

import com.arturo.clients_microservice.dtos.ClientDto;
import com.arturo.clients_microservice.dtos.MetricsDto;
import com.arturo.clients_microservice.entities.Client;

import java.util.List;

public interface ClientService {
    MetricsDto seeClientsMetrics();

    List<ClientDto> findAllClients();

    Client saveClient(ClientDto client);
}
