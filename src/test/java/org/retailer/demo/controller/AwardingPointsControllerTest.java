package org.retailer.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.retailer.demo.domain.dto.PointsByMonth;
import org.retailer.demo.exception.AwardingPointsException;
import org.retailer.demo.service.SpendingsService;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AwardingPointsControllerTest {

    @Mock
    SpendingsService spendingsService;
    @InjectMocks
    AwardingPointsController awardingPointsController;

    LocalDate date;
    LocalDate dateException;

    @BeforeEach
    void setUp() {
        date = LocalDate.now();
        dateException = LocalDate.now().plusDays(1);
    }

    @Test
    void getAwardingPointsTest() {
        ResponseEntity<List<PointsByMonth>> pointsByMonths = awardingPointsController.getAwardingPoints(date);
        log.info("Response object: {}", pointsByMonths);
        assertSame(pointsByMonths.getStatusCode(), ResponseEntity.ok().build().getStatusCode());
    }

    @Test
    void getAwardingPointsInvalidaDateTest() {
        Throwable tr = assertThrows(Exception.class, () -> awardingPointsController.getAwardingPoints(null));
        log.info("Exception message: {}", tr.getLocalizedMessage());
        assertEquals("Required request parameter 'fromDate' for method parameter type LocalDate is not present", tr.getLocalizedMessage());
    }

    @Test
    void getAwardingPointsExceptionDateInFutureTest() {
        Throwable tr = assertThrows(Exception.class, () -> awardingPointsController.getAwardingPoints(dateException));
        log.info("Exception message: {}", tr.getLocalizedMessage());
        assertEquals("fromDate cannot be in the future", tr.getLocalizedMessage());
    }

    @Test
    void exceptionHandlerDateTest() {
        ResponseEntity<String> responseMessage = awardingPointsController.exceptionHandler(new AwardingPointsException("Exception"));
        log.info("Response object: {}", responseMessage);
        assertEquals(responseMessage.getStatusCode(), ResponseEntity.badRequest().build().getStatusCode());
        assertEquals("Exception", responseMessage.getBody());
    }
}