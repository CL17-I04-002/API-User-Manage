package com.project.user.manage.controller;


import com.project.user.manage.entity.Enterprise;
import com.project.user.manage.repository.IEnterpriseRepository;
import com.project.user.manage.util.BindingResultUtil;
import com.project.user.manage.util.DetailedValidationGroup;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/enterprise")
public class EnterpriseController {
    private final IEnterpriseRepository enterpriseRepository;
    private static final String MAP_RESPONSE_BAD_REQUEST = "mapResponseBadRequest";
    private static final String MAP_VALUE_TRUE = "mapValueTrue";
    private static final String MAP_RESPONSE_SUCCESS = "mapResponseSuccess";

    @Autowired
    public EnterpriseController(IEnterpriseRepository enterpriseRepository){
        this.enterpriseRepository = enterpriseRepository;
    }
    @GetMapping
    public ResponseEntity<List<Enterprise>> getAllEnterprise(
            @Nullable @RequestParam() Integer page,
            @Nullable @RequestParam() Integer size){
        List<Enterprise> lstEnterprise;
        if(page == null || size == null){
            return ResponseEntity.status(HttpStatus.OK).body(enterpriseRepository.findAll());
        } else {
            Pageable pageable = (Pageable) PageRequest.of(page, size);
            Page<Enterprise> enterprisePage = enterpriseRepository.findAll((org.springframework.data.domain.Pageable) pageable);
            lstEnterprise = enterprisePage.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstEnterprise);
        }
    }
    @PostMapping
    public ResponseEntity<String> createEnterprise(@RequestBody @Validated(DetailedValidationGroup.class) Enterprise enterprise, BindingResult bindingResult){
        Map<String, Object> mapResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al registrar una empresa, por favor ingrese los campos necesarios", "Se agrego correctamente la empresa", enterprise);
        if(mapResult.get(MAP_VALUE_TRUE) != null) return (ResponseEntity<String>) mapResult.get(MAP_VALUE_TRUE);
         else {
            enterpriseRepository.save(enterprise);
            return (ResponseEntity<String>) mapResult.get(MAP_RESPONSE_SUCCESS);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEnterprise(@RequestBody @Validated(DetailedValidationGroup.class) Enterprise enterprise, @PathVariable(value = "id") Long id, BindingResult bindingResult){
        Enterprise enterpriseFound = enterpriseRepository.findById(id).orElse(null);
        Map<String, Object> mapResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al modificar una empresa, por favor ingrese los campos necesarios", "Se modifico correctamente la empresa", enterprise);
        if(enterpriseFound != null){
            if(mapResult.get(MAP_VALUE_TRUE) != null) return (ResponseEntity<String>) mapResult.get(MAP_RESPONSE_BAD_REQUEST);
            else {
                enterpriseFound.setName(enterprise.getName());
                enterpriseFound.setContactEmail(enterprise.getContactEmail());
                enterpriseFound.setContactPhone(enterprise.getContactPhone());
                enterpriseFound.setAddress(enterprise.getAddress());
                enterpriseRepository.save(enterpriseFound);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la empresa");
        }
        return (ResponseEntity<String>) mapResult.get(MAP_RESPONSE_SUCCESS);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnterprise(@PathVariable(value = "id") Long id){
        Enterprise enterpriseFound = enterpriseRepository.findById(id).orElse(null);
        if(enterpriseFound != null){
            enterpriseRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la empresa");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la empresa");
        }
    }
}