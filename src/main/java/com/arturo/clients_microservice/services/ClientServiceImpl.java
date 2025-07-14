package com.arturo.clients_microservice.services;

import com.arturo.clients_microservice.dtos.ClientDto;
import com.arturo.clients_microservice.dtos.MetricsDto;
import com.arturo.clients_microservice.entities.Client;
import com.arturo.clients_microservice.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public MetricsDto seeClientsMetrics() {
        //Set metrics like average and standard deviation
        List<Integer> ages = clientRepository.findAll().stream().map(Client::getAge).collect(Collectors.toList());

        if (ages.isEmpty()) {
            return new MetricsDto(0.0, 0.0);
        }

        double average = ages.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        double variance = ages.stream()
                .mapToDouble(age -> Math.pow(age - average, 2))
                .average()
                .orElse(0.0);

        DecimalFormat df = new DecimalFormat("#.##");

        double stdDeviation = Double.parseDouble(df.format(Math.sqrt(variance)));

        return new MetricsDto(average, stdDeviation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> findAllClients() {
        final int LIFE_EXPECTATION = 75;
        LocalDate now = LocalDate.now();
        List<Client> clients = clientRepository.findAll();

        return clients.stream().map(client -> {
            LocalDate lifeExpectation = null;
            Integer remainingYears = null;

            if (client.getBirthday() != null) {
                lifeExpectation = client.getBirthday().plusYears(LIFE_EXPECTATION);

                //Only if the date is in the future
                if (!lifeExpectation.isBefore(now)) {
                    remainingYears = Period.between(now, lifeExpectation).getYears();
                } else {
                    remainingYears = 0;
                }
            }

            return new ClientDto(
                    client.getName(),
                    client.getSurname(),
                    client.getAge(),
                    client.getBirthday(),
                    lifeExpectation,
                    remainingYears
            );
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Client saveClient(ClientDto clientDto) {
        int calculatedAge = Period.between(clientDto.getBirthday(), LocalDate.now()).getYears();

        if (!calculatedAgeEquals(clientDto.getAge(), calculatedAge)) {
            clientDto.setAge(calculatedAge);
        }

        Client client = new Client();
        client.setName(clientDto.getName());
        client.setSurname(clientDto.getSurname());
        client.setAge(clientDto.getAge());
        client.setBirthday(clientDto.getBirthday());
        client.setCreatedAt(LocalDate.now());
        client.setUpdatedAt(LocalDate.now());

        return clientRepository.save(client);
    }

    private boolean calculatedAgeEquals(Integer inputAge, int realAge) {
        if (inputAge == null) return false;
        return inputAge == realAge;
    }
}
