package org.retailer.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.retailer.demo.domain.dto.AwardingPoints;
import org.retailer.demo.domain.dto.PointsByMonth;
import org.retailer.demo.domain.entity.Customer;
import org.retailer.demo.domain.entity.Spendings;
import org.retailer.demo.exception.AwardingPointsException;
import org.retailer.demo.repository.CustomerRepository;
import org.retailer.demo.repository.SpendingsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Slf4j
@Service
public class SpendingsService {

    private final SpendingsRepository spendingsRepository;
    private final CustomerRepository customerRepository;
    private final int monthsToSearch;

    public SpendingsService(
            SpendingsRepository spendingsRepository,
            CustomerRepository customerRepository,
            @Value("${monthsToSearch}") int monthsToSearch) {
        this.spendingsRepository = spendingsRepository;
        this.customerRepository = customerRepository;
        this.monthsToSearch = monthsToSearch;
    }

    public List<PointsByMonth> getAwardingPoints(LocalDate date) throws AwardingPointsException {
        List<PointsByMonth> pointsInRange = new ArrayList<>();

        if (monthsToSearch < 1) {
            throw new AwardingPointsException("monthsToSearch env value must be greater than 0");
        }

        try {
//            iterate over existing customers
            for (Customer customer : CollectionUtils.emptyIfNull(customerRepository.findAll())) {

                int idxMonth = 0;
                LocalDate fromDate = date;
                PointsByMonth pointsByMonth = new PointsByMonth();
                pointsByMonth.setCustomerName(customer.getName());

                while (idxMonth++ < monthsToSearch) {

//                    for each month we create a new AwardingPoints object
                    pointsByMonth.getAwardedPoints().add(
                            new AwardingPoints(
                                    new StringJoiner(" / ")
                                            .add(fromDate.toString())
                                            .add(fromDate.plusMonths(1).minusDays(1).toString()).toString(),
                                    spendingsRepository.findAllByCustomerIdAndDateBetween(
                                                    customer.getId(),
                                                    fromDate, fromDate.plusMonths(1).minusDays(1))
                                            .stream()
                                            .reduce(0, (subtotal, spending) -> subtotal + (int)spending.getPoints(), Integer::sum)
                            )
                    );

                    fromDate = fromDate.plusMonths(1);
                }

//                we add the client earned points to the list
                pointsInRange.add(pointsByMonth);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

        if (pointsInRange.isEmpty()) {
            throw new AwardingPointsException("No customers found");
        }

        return pointsInRange;
    }

    public Spendings createSpendings(Spendings spendings) {
        return spendingsRepository.save(spendings);
    }

    public Optional<Spendings> getSpendingsById(Long id) {
        return spendingsRepository.findById(id);
    }

    public List<Spendings> getAllSpendings() {
        return spendingsRepository.findAll();
    }

    public Spendings updateSpendings(Long id, Spendings updatedSpendings) {
        Optional<Spendings> existingSpendingsOptional = spendingsRepository.findById(id);

        if (existingSpendingsOptional.isPresent()) {
            Spendings existingSpendings = existingSpendingsOptional.get();
            existingSpendings.setCustomer(updatedSpendings.getCustomer());
            existingSpendings.setDate(updatedSpendings.getDate());
            existingSpendings.setAmount(updatedSpendings.getAmount());
            return spendingsRepository.save(existingSpendings);
        } else {
            // Handle not found case, you may throw an exception or return null/empty object
            return null;
        }
    }

    public void deleteSpendings(Long id) {
        spendingsRepository.deleteById(id);
    }

}
