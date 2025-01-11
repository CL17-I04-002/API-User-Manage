package com.project.user.manage.entity;

import com.project.user.manage.util.BasicValidationGroup;
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
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enterprise_id", nullable = false, referencedColumnName = "id")
    private Enterprise enterprise;
    @Column(name = "email", length = 100, nullable = true)
    private String email;
    @Column(name = "phone", length = 50, nullable = true)
    private String phone;
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "address", length = 200, nullable = false)
    private String address;
}