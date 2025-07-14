package com.arturo.clients_microservice.controllers;

import com.arturo.clients_microservice.dtos.ClientDto;
import com.arturo.clients_microservice.dtos.MetricsDto;
import com.arturo.clients_microservice.dtos.ResponseDto;
import com.arturo.clients_microservice.entities.Client;
import com.arturo.clients_microservice.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    final private ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ClientDto>>> findAllClients() {
        ResponseDto<List<ClientDto>> responseDto;
        List<ClientDto> clients = clientService.findAllClients();
        HttpStatus status;
        if(clients.isEmpty()){
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ResponseDto<>(false, "No existen clientes", status.value(), clients);
        } else {
            status = HttpStatus.OK;
            responseDto = new ResponseDto<>(true, "Clientes encontrados",  status.value(), clients);
        }

        return new ResponseEntity<>(responseDto, status);
    }

    @GetMapping("/metrics")
    public ResponseEntity<ResponseDto<MetricsDto>> seeClientsMetrics () {
        MetricsDto metrics = clientService.seeClientsMetrics();
        ResponseDto<MetricsDto> responseDto;
        HttpStatus status;
        if (metrics.getAverage().equals(0.0) || metrics.getDeviation().equals(0.0)){
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ResponseDto<>(false, "No existen suficientes clientes para calcular métricas", status.value(), metrics);
        } else {
            status = HttpStatus.OK;
            responseDto = new ResponseDto<>(true, "Métricas calculadas", status.value(), metrics);
        }

        return new ResponseEntity<>(responseDto, status);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Client>> saveClient(@RequestBody ClientDto clientDto) {
        try{
            Client client =  clientService.saveClient(clientDto);
            ResponseDto<Client> responseDto;
            responseDto = new ResponseDto<>(true, "Cliente creado correctamente", HttpStatus.OK.value(), client);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch(IllegalArgumentException e){
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
