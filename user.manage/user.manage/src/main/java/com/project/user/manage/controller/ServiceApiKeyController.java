package com.project.user.manage.controller;

import com.project.user.manage.entity.ServiceApiKey;
import com.project.user.manage.repository.IServiceApiKeyRepository;
import com.project.user.manage.service.ICustomApiKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/serviceApiKey")
public class ServiceApiKeyController {
    private final ICustomApiKeyManager customApiKeyManager;
    private final IServiceApiKeyRepository serviceApiKeyRepository;
    @Autowired
    public ServiceApiKeyController(ICustomApiKeyManager customApiKeyManager,
                                    IServiceApiKeyRepository serviceApiKeyRepository){
        this.customApiKeyManager = customApiKeyManager;
        this.serviceApiKeyRepository = serviceApiKeyRepository;
    }
    @PostMapping
    public ResponseEntity<String> createServiceApiKey(@RequestBody ServiceApiKey serviceApiKey){
        String apiKey = customApiKeyManager.addServiceApiKey(serviceApiKey);
        if(apiKey != null) return ResponseEntity.status(HttpStatus.OK).body("Api Key: " + apiKey);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error inesperado");
    }
    @PostMapping("/validate")
    public ResponseEntity<String> validateApiKey(@RequestBody String apiKey){
        Optional<ServiceApiKey> optionalServiceApiKey = serviceApiKeyRepository.findByApiKey(apiKey);
        if(optionalServiceApiKey.isPresent()) return ResponseEntity.ok().body(optionalServiceApiKey.get().apiKey);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el API Key");
    }
}
