package com.project.user.manage.repository;

import com.project.user.manage.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnterpriseRepository extends JpaRepository<Enterprise, Long> {

}
