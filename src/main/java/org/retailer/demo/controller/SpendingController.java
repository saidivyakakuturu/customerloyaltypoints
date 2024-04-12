package org.retailer.demo.controller;

import jakarta.validation.Valid;
import org.retailer.demo.domain.entity.Spendings;
import org.retailer.demo.service.SpendingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class SpendingController {

    @Autowired
    private SpendingsService spendingsService;

    @PostMapping
    public ResponseEntity<Spendings> createSpendings(@Valid @RequestBody Spendings spendings) {
        Spendings createdSpendings = spendingsService.createSpendings(spendings);
        return new ResponseEntity<>(createdSpendings, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Spendings>> getSpendingsById(@PathVariable Long id) {
        Optional<Spendings> spendings = spendingsService.getSpendingsById(id);
        return ResponseEntity.ok(spendings);
    }

    @GetMapping
    public ResponseEntity<Object> getAllSpendings() {
        List<Spendings> spendingsList = spendingsService.getAllSpendings();
        return ResponseEntity.ok(spendingsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spendings> updateSpendings(@PathVariable Long id, @Valid @RequestBody Spendings spendingsDetails) {
        Spendings updatedSpendings = spendingsService.updateSpendings(id, spendingsDetails);
        return ResponseEntity.ok(updatedSpendings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpendings(@PathVariable Long id) {
        spendingsService.deleteSpendings(id);
        return ResponseEntity.noContent().build();
    }

}