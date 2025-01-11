package com.project.user.manage.controller;

import com.project.user.manage.entity.CustomerTask;
import com.project.user.manage.repository.ICustomerTaskRepository;
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
@RequestMapping("/api/v1/customerTask")
public class CustomerTaskController {
    private final ICustomerTaskRepository customerTaskRepository;
    @Autowired
    public CustomerTaskController(ICustomerTaskRepository customerTaskRepository){
        this.customerTaskRepository = customerTaskRepository;
    }
    @GetMapping
    public ResponseEntity<?> getAllCustomerTask(
            @Nullable @RequestParam() Long id,
            @Nullable @RequestParam() Integer size,
            @Nullable @RequestParam() Integer page){
        List<CustomerTask> lstCustomerTask;
        if(id != null){
            CustomerTask customerTaskFound = customerTaskRepository.findById(id).orElseThrow();
            if(customerTaskFound != null){
                return ResponseEntity.status(HttpStatus.OK).body(customerTaskFound);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        }
        else if(size == null || page == null){
            return ResponseEntity.status(HttpStatus.OK).body(customerTaskRepository.findAll());
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<CustomerTask> customerPage = customerTaskRepository.findAll(pageable);
            lstCustomerTask = customerPage.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstCustomerTask);
        }
    }
    @PostMapping
    public ResponseEntity<String> createCustomerTask(@RequestBody @Validated(DetailedValidationGroup.class) CustomerTask customerTask, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al registrar un cliente tarea, por favor ingrese los campos necesarios", "Se agrego correctamente el cliente tarea", null);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
        else {
            customerTaskRepository.save(customerTask);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomerTask(@RequestBody @Validated(DetailedValidationGroup.class) CustomerTask customerTask, @PathVariable(value = "id") Long id, BindingResult bindingResult){
        CustomerTask customerTaskFound = customerTaskRepository.findById(id).orElse(null);
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al modificar un cliente tarea, por favor ingrese los campos necesarios", "Se modifico correctamente el cliente tarea", null);
        if(customerTaskFound != null){
            if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
            else {
                customerTaskFound.setDescription(customerTask.getDescription());
                customerTaskFound.setLimitDate(customerTask.getLimitDate());
                customerTaskFound.setState(customerTask.getState());
                customerTaskFound.setCustomer(customerTask.getCustomer());
                customerTaskRepository.save(customerTask);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente tarea");
        }
        return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerTask(@PathVariable(value = "id") Long id){
        CustomerTask customerTaskFound = customerTaskRepository.findById(id).orElse(null);
        if(customerTaskFound != null){
            customerTaskRepository.delete(customerTaskFound);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el cliente tarea");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente tarea");
        }
    }
}