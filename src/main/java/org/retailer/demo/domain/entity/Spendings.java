package org.retailer.demo.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.retailer.demo.domain.enums.PointAmounts;


import java.time.LocalDate;

@Data
@Entity
@Table(name="spendings")
@AllArgsConstructor
@NoArgsConstructor
public class Spendings {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Schema(name = "spendings id", example = "1")
    private long id;

    @NotBlank
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name="customer_id", nullable=false)
    @Schema(name = "scustomer")
    private Customer customer;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Schema(name = "spendings description", example = "shoes")
    private String description;

    @Column(name = "amount", nullable = false)
    @NotBlank
    @Schema(name = "spendings amount", example = "100")
    private double amount;

    @Column(name = "date", nullable = false)
    @NotBlank
    @Schema(name = "spendings date", example = "1")
    private LocalDate date;

    public double getPoints() {
        return (this.amount > PointAmounts.FIRST_POINTS.getValue() ?
                this.amount - PointAmounts.FIRST_POINTS.getValue() : 0) +
                (this.amount > PointAmounts.SECOND_POINTS.getValue() ?
                this.amount - PointAmounts.SECOND_POINTS.getValue() : 0);
    }
}
