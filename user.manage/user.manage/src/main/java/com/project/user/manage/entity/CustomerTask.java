package com.project.user.manage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "CustomerTask")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerTask implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", length = 200, nullable = true)
    private String description;
    @NotNull
    @Column(name = "limit_date", nullable = false)
    private LocalDate limitDate;
    @NotNull
    @Column(name = "state", length = 15, nullable = false)
    private String state;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id")
    private Customer customer;
}
