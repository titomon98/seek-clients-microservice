package com.arturo.clients_microservice.services;

import com.arturo.clients_microservice.dtos.ClientDto;
import com.arturo.clients_microservice.entities.Client;
import com.arturo.clients_microservice.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClassServiceImplTest {
    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void testFindAllClients() {
        LocalDate birthday = LocalDate.of(1998, 5, 2);
        Client client = new Client();
        client.setName("Arturo");
        client.setSurname("Monterroso");
        client.setAge(27);
        client.setBirthday(birthday);

        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientDto> result = clientService.findAllClients();

        assertEquals(1, result.size());
        ClientDto dto = result.get(0);

        assertEquals("Arturo", dto.getName());
        assertEquals("Monterroso", dto.getSurname());
        assertEquals(27, dto.getAge());
        assertNotNull(dto.getLifeExpectation());
        assertNotNull(dto.getRemainingYears());
    }

    @Test
    void testFindAllClientsWhenBirthdayIsNull() {
        Client client = new Client();
        client.setName("Arturo");
        client.setSurname("Monterroso");
        client.setAge(30);
        client.setBirthday(null);

        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientDto> result = clientService.findAllClients();

        assertEquals(1, result.size());
        ClientDto dto = result.get(0);

        assertEquals("Arturo", dto.getName());
        assertNull(dto.getLifeExpectation());
        assertNull(dto.getRemainingYears());
    }

    @Test
    void testFindAllClientsWhenClientLifeExpectationIsZero() {
        LocalDate birthday = LocalDate.now().minusYears(80);
        Client client = new Client();
        client.setName("Arturo");
        client.setSurname("Monterroso");
        client.setAge(80);
        client.setBirthday(birthday);

        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientDto> result = clientService.findAllClients();

        ClientDto dto = result.get(0);
        assertEquals(0, dto.getRemainingYears());
    }

    @Test
    void saveClientWhenAgeIsIncorrect() {
        LocalDate birthday = LocalDate.of(1998, 5, 2);
        int wrongAge = 20;
        int expectedAge = Period.between(birthday, LocalDate.now()).getYears();

        ClientDto dto = new ClientDto("Arturo", "Monterroso", wrongAge, birthday, null, null);

        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Client savedClient = clientService.saveClient(dto);

        assertEquals("Arturo", savedClient.getName());
        assertEquals("Monterroso", savedClient.getSurname());
        assertEquals(expectedAge, savedClient.getAge());
        assertNotNull(savedClient.getCreatedAt());
        assertNotNull(savedClient.getUpdatedAt());

        verify(clientRepository, times(1)).save(any(Client.class));
    }
}
