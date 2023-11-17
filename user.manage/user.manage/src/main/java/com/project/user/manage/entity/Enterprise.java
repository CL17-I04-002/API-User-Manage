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
@Table(name = "Enterprise")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enterprise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "name", length = 200, nullable = false)
    private String name;
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "contact_email", length = 100, nullable = false)
    private String contactEmail;
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "contact_phone", length = 100, nullable = false)
    private String contactPhone;
    @Column(name = "address", length = 250, nullable = true)
    private String address;
}