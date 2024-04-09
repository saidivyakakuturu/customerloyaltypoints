package org.retailer.demo.repository;

import org.retailer.demo.domain.entity.Spendings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SpendingsRepository extends JpaRepository<Spendings, Long> {
    List<Spendings> findAllByCustomerIdAndDateBetween(
            @Param("customerId") Long customerId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);
}
