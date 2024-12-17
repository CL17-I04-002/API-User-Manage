package com.project.user.manage.controller;


import com.project.user.manage.entity.Enterprise;
import com.project.user.manage.repository.IEnterpriseRepository;
import com.project.user.manage.util.BindingResultUtil;
import com.project.user.manage.util.DetailedValidationGroup;
import com.project.user.manage.util.KeysData;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/enterprise")
public class EnterpriseController {
    private final IEnterpriseRepository enterpriseRepository;

    @Autowired
    public EnterpriseController(IEnterpriseRepository enterpriseRepository){
        this.enterpriseRepository = enterpriseRepository;
    }
    @GetMapping
    public ResponseEntity<?> getAllEnterprise(
            @Nullable @RequestParam(required = false) Long id,
            @Nullable @RequestParam(required = false) Integer page,
            @Nullable @RequestParam(required = false) Integer size){
        List<Enterprise> lstEnterprise;
        if(id != null){
            return ResponseEntity.status(HttpStatus.OK).body(enterpriseRepository.findById(id));
        } else if(page == null || size == null){
            return ResponseEntity.status(HttpStatus.OK).body(enterpriseRepository.findAll());
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Enterprise> enterprisePage = enterpriseRepository.findAll(pageable);
            lstEnterprise = enterprisePage.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstEnterprise);
        }
    }
    @PostMapping
    public ResponseEntity<String> createEnterprise(@RequestBody @Validated(DetailedValidationGroup.class) Enterprise enterprise, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al registrar una empresa, por favor ingrese los campos necesarios", "Se agrego correctamente la empresa", enterprise);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
         else {
            enterpriseRepository.save(enterprise);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEnterprise(@RequestBody @Validated(DetailedValidationGroup.class) Enterprise enterprise, @PathVariable(value = "id") Long id, BindingResult bindingResult){
        Enterprise enterpriseFound = enterpriseRepository.findById(id).orElse(null);
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al modificar una empresa, por favor ingrese los campos necesarios", "Se modifico correctamente la empresa", enterprise);
        if(enterpriseFound != null){
            if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
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
        return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
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