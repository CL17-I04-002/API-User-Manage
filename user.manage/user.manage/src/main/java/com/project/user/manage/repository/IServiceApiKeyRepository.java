package com.project.user.manage.repository;

import com.project.user.manage.entity.ServiceApiKey;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface IServiceApiKeyRepository extends JpaRepository<ServiceApiKey, Long> {
    Optional<ServiceApiKey> findByApiKey(String apiKey);
}
