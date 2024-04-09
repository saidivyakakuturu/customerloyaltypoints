package org.retailer.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.retailer.demo.domain.dto.PointsByMonth;
import org.retailer.demo.domain.entity.Customer;
import org.retailer.demo.domain.entity.Spendings;
import org.retailer.demo.repository.CustomerRepository;
import org.retailer.demo.repository.SpendingsRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpendingsServiceTest {

    SpendingsRepository spendingsRepository;
    CustomerRepository customerRepository;

    SpendingsService spendingsService;

    LocalDate date;
    List<Customer> customers;
    List<Spendings> spendingsFisrtMonth;
    List<Spendings> spendingsSecondMonth;
    List<Spendings> spendingsThirdMonth;

    @BeforeEach
    void setUp() {
        customers = List.of(new Customer(1, "John Doe", "test@mail.com"));
        spendingsFisrtMonth = List.of(
                new Spendings(1, customers.get(0), "magazines", 60, LocalDate.now()),
                new Spendings(2, customers.get(0), "candy", 70, LocalDate.now().plusDays(15))
        );

        spendingsSecondMonth = List.of(
                new Spendings(3, customers.get(0), "shoes", 80, LocalDate.now().plusMonths(1)),
                new Spendings(4, customers.get(0), "suit", 110, LocalDate.now().plusMonths(1).plusDays(15))
        );

        spendingsThirdMonth = List.of(
                new Spendings(3, customers.get(0), "shoes", 80, LocalDate.now().plusMonths(1)),
                new Spendings(4, customers.get(0), "suit", 200, LocalDate.now().plusMonths(1).plusDays(15))
                );

        date = LocalDate.now();
        spendingsRepository = Mockito.mock(SpendingsRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        spendingsService = new SpendingsService(spendingsRepository, customerRepository, 3);
    }

    @Test
    void getAwardingPointsTest() {
        when(customerRepository.findAll()).thenReturn(customers);
        when(spendingsRepository.findAllByCustomerIdAndDateBetween(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenAnswer(
                (Answer<List<Spendings>>) invocation -> {
                    if (LocalDate.now().equals(invocation.getArgument(1))){
                        return spendingsFisrtMonth;
                    }
                    else if(LocalDate.now().plusMonths(1).equals(invocation.getArgument(1))) {
                        return spendingsSecondMonth;
                    }
                    return spendingsThirdMonth;
                });
        List<PointsByMonth> points = spendingsService.getAwardingPoints(date);

        log.info("customer name: {}", points.get(0).getCustomerName());
        assertEquals("John Doe", points.get(0).getCustomerName());
        log.info("number of months searched (3): {}", points.get(0).getAwardedPoints().size());
        assertEquals(3, points.get(0).getAwardedPoints().size());
        log.info("Earned points fisrt month: {}", points.get(0).getAwardedPoints().get(0).getPoints());
        assertEquals(30, points.get(0).getAwardedPoints().get(0).getPoints());
        log.info("Earned points second month: {}", points.get(0).getAwardedPoints().get(1).getPoints());
        assertEquals(100, points.get(0).getAwardedPoints().get(1).getPoints());
        log.info("Earned points third month: {}", points.get(0).getAwardedPoints().get(2).getPoints());
        assertEquals(280, points.get(0).getAwardedPoints().get(2).getPoints());
        log.info("Earned points total: {}", points.get(0).getTotalPoints());
        assertEquals(410, points.get(0).getTotalPoints());
    }

    @Test
    void getAwardingPointsNoCustomersTest() {
        when(customerRepository.findAll()).thenReturn(null);
        Throwable tr = assertThrows(Exception.class, () -> spendingsService.getAwardingPoints(date));
        log.info("Exception message: {}", tr.getLocalizedMessage());
        assertEquals("No customers found", tr.getLocalizedMessage());
    }

    @Test
    void getAwardingPointsMonthsExceptionTest() {
        spendingsService = new SpendingsService(spendingsRepository, customerRepository, 0);
        Throwable tr = assertThrows(Exception.class, () -> spendingsService.getAwardingPoints(date));
        log.info("Exception message: {}", tr.getLocalizedMessage());
        assertEquals("monthsToSearch env value must be greater than 0", tr.getLocalizedMessage());
    }
}