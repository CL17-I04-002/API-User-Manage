package com.project.user.manage.controller;

import com.project.user.manage.entity.ServiceApiKey;
import com.project.user.manage.service.ICustomApiKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/serviceApiKey")
public class ServiceApiKeyController {
    private final ICustomApiKeyManager customApiKeyManager;
    @Autowired
    public ServiceApiKeyController(ICustomApiKeyManager customApiKeyManager){
        this.customApiKeyManager = customApiKeyManager;
    }
    @PostMapping
    public ResponseEntity<String> createServiceApiKey(@RequestBody ServiceApiKey serviceApiKey){
        String apiKey = customApiKeyManager.addServiceApiKey(serviceApiKey);
        if(apiKey != null) return ResponseEntity.status(HttpStatus.OK).body("Api Key: " + apiKey);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error inesperado");
    }
}
