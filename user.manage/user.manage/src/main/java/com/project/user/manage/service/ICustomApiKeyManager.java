package com.project.user.manage.service;

import com.project.user.manage.entity.ServiceApiKey;

public interface ICustomApiKeyManager {
    String addServiceApiKey(ServiceApiKey serviceApiKey);
    boolean validateApiKey(String keyName, String apiKey);
}
