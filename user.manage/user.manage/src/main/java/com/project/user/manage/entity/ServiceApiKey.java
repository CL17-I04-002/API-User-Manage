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
@Table(name = "ServiceApiKey")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceApiKey implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "service", length = 200, nullable = false)
    public String service;
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "apiKey", length = 200, nullable = false)
    public String apiKey;
}
