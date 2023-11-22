package com.project.user.manage.controller;

import com.project.user.manage.entity.Customer;
import com.project.user.manage.repository.ICustomerRepository;
import com.project.user.manage.util.BindingResultUtil;
import com.project.user.manage.util.DetailedValidationGroup;
import com.project.user.manage.util.KeysData;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final ICustomerRepository customerRepository;
    @Autowired
    public CustomerController(ICustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer(
            @Nullable @RequestParam() Integer size,
            @Nullable @RequestParam() Integer page){
        List<Customer> lstCustomer;
        if(size == null || page == null){
            return ResponseEntity.status(HttpStatus.OK).body(customerRepository.findAll());
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Customer> customerPage = customerRepository.findAll(pageable);
            lstCustomer = customerPage.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstCustomer);
        }
    }
    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Validated(DetailedValidationGroup.class) Customer customer, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al registrar un cliente, por favor ingrese los campos necesarios", "Se agrego correctamente el cliente", customer);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
        else {
            customerRepository.save(customer);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@RequestBody @Validated(DetailedValidationGroup.class) Customer customer, @PathVariable(value = "id") Long id, BindingResult bindingResult){
        Customer customerFound = customerRepository.findById(id).orElse(null);
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al modificar un cliente, por favor ingrese los campos necesarios", "Se modifico correctamente el cliente", customer);
        if(customerFound != null){
            if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
            else {
                customerFound.setName(customer.getName());
                customerFound.setLastName(customer.getLastName());
                customerFound.setEnterprise(customer.getEnterprise());
                customerFound.setEmail(customer.getEmail());
                customerFound.setPhone(customer.getPhone());
                customerFound.setAddress(customer.getAddress());
                customerRepository.save(customerFound);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente");
        }
        return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") Long id){
        Customer customerFound = customerRepository.findById(id).orElse(null);
        if(customerFound != null){
            customerRepository.delete(customerFound);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el cliente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente");
        }
    }
}