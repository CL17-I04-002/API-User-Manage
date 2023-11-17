package com.project.user.manage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @NotNull
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enterprise_id", nullable = false, referencedColumnName = "id")
    private Enterprise enterprise;
    @Column(name = "email", length = 100, nullable = true)
    private String email;
    @Column(name = "phone", length = 50, nullable = true)
    private String phone;
    @NotNull
    @Column(name = "address", length = 200, nullable = false)
    private String address;
}