package org.retailer.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.retailer.demo.controller.SpendingController;
import org.retailer.demo.domain.entity.Customer;
import org.retailer.demo.domain.entity.Spendings;
import org.retailer.demo.service.SpendingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpendingControllerTests {

    @Mock
    private SpendingsService spendingsService;

    @InjectMocks
    private SpendingController spendingController;

    @Test
    public void testCreateSpendings() {
        // Mocked Spendings object
        Spendings spendings = Spendings.builder()
                .date(LocalDate.now())
                .id(2)
                .description("new")
                .amount(200)
                .customer(Customer.builder()
                        .id(2)
                        .email("xyz@acb.com")
                        .build())
                .build();

        // Mock behavior of spendingsService.createSpendings
        when(spendingsService.createSpendings(any(Spendings.class))).thenReturn(spendings);

        // Invoke controller method
        ResponseEntity<Spendings> response = spendingController.createSpendings(spendings);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(spendings, response.getBody());
    }

    @Test
    public void testGetSpendingsById() {
        long id = 1L;
        Spendings spendings = Spendings.builder()
                .id(id)
                .description("Sample Spendings")
                .build();

        // Mock behavior of spendingsService.getSpendingsById
        when(spendingsService.getSpendingsById(id)).thenReturn(Optional.of(spendings));

        // Invoke controller method
        ResponseEntity<Optional<Spendings>> response = spendingController.getSpendingsById(id);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(spendings, response.getBody().get());
    }

    @Test
    public void testGetAllSpendings() {
        List<Spendings> spendingsList = new ArrayList<>();
        spendingsList.add(Spendings.builder().id(1L).description("Spendings 1").build());
        spendingsList.add(Spendings.builder().id(2L).description("Spendings 2").build());

        // Mock behavior of spendingsService.getAllSpendings
        when(spendingsService.getAllSpendings()).thenReturn(spendingsList);

        // Invoke controller method
        ResponseEntity<Object> response = spendingController.getAllSpendings();

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(spendingsList, response.getBody());
    }

    @Test
    public void testUpdateSpendings() {
        long id = 1L;
        Spendings existingSpendings = Spendings.builder().id(id).description("Existing Spendings").build();
        Spendings updatedSpendings = Spendings.builder().id(id).description("Updated Spendings").build();

        // Mock behavior of spendingsService.updateSpendings
        when(spendingsService.updateSpendings(eq(id), any(Spendings.class))).thenReturn(updatedSpendings);

        // Invoke controller method
        ResponseEntity<Spendings> response = spendingController.updateSpendings(id, updatedSpendings);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedSpendings, response.getBody());
    }

    @Test
    public void testDeleteSpendings() {
        long id = 1L;

        // Mock behavior of spendingsService.deleteSpendings
        doNothing().when(spendingsService).deleteSpendings(id);

        // Invoke controller method
        ResponseEntity<Void> response = spendingController.deleteSpendings(id);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
