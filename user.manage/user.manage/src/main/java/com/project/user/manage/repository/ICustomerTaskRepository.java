package com.project.user.manage.repository;

import com.project.user.manage.entity.CustomerTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerTaskRepository extends JpaRepository<CustomerTask, Long> {

}
