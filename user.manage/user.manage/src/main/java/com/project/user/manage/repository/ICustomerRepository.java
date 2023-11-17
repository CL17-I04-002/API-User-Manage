package com.project.user.manage.repository;

import com.project.user.manage.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

}
