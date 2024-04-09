package org.retailer.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.retailer.demo.domain.dto.PointsByMonth;
import org.retailer.demo.exception.AwardingPointsException;
import org.retailer.demo.service.SpendingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("rewardingPoints/v1")
public class AwardingPointsController {
    @Autowired
    private SpendingsService spendingsService;

    @GetMapping(produces = { "application/json" })
    @Operation(description = "Get all user by user name", operationId = "getAllUserByName", tags={ "PointsByMonth"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PointsByMonth.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid fromDate supplied", content = @Content(schema = @Schema(implementation = String.class))),
    })
    public ResponseEntity<List<PointsByMonth>> getAwardingPoints(
            @RequestParam(name = "fromDate", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate) {
        if (Objects.isNull(fromDate)) {
            throw new AwardingPointsException("Required request parameter 'fromDate' for method parameter type LocalDate is not present");
        }
        if (fromDate.isAfter(LocalDate.now())) {
            throw new AwardingPointsException("fromDate cannot be in the future");
        }
        return ResponseEntity.ok(spendingsService.getAwardingPoints(fromDate));
    }

    @ExceptionHandler({AwardingPointsException.class, Exception.class})
    public ResponseEntity<String> exceptionHandler(Exception ex) {
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
    }
}
