package com.project.user.manage.service;

import com.project.user.manage.entity.ServiceApiKey;
import com.project.user.manage.repository.IServiceApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CustomApiKeyManager implements ICustomApiKeyManager{
    @Autowired
    private IServiceApiKeyRepository serviceApiKeyRepository;
    private SecureRandom secureRandom = new SecureRandom();
    private String generateApiKey() {
        byte[] apiKeyBytes = new byte[32];
        secureRandom.nextBytes(apiKeyBytes);
        return Base64.getEncoder().encodeToString(apiKeyBytes);
    }

    @Override
    public String addServiceApiKey(ServiceApiKey serviceApiKey) {
        try{
            String apiKey = generateApiKey();
            serviceApiKey.setApiKey(apiKey);
            serviceApiKeyRepository.save(serviceApiKey);
            return apiKey;
        } catch (Exception e){
            System.out.println("Error" + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean validateApiKey(String keyName, String apiKey) {
        ServiceApiKey apiKeyFound = serviceApiKeyRepository.findByApiKey(apiKey).orElse(null);
        return apiKeyFound != null && keyName.equals("X-API-KEY");
    }
}