package org.retailer.demo.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@Table(name="customer")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Schema(name = "customer id", example = "1")
    private long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Schema(name = "customer name", example = "John Doe")
    private String name;

    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    @Schema(name = "customer email", example = "test@mail.com")
    private String email;
}
