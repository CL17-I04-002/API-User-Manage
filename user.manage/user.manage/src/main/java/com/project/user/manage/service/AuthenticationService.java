package com.project.user.manage.service;

import com.project.user.manage.security.auth.ApiKeyAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService{
    @Value("${api.key.key}")
    private String apiKeyValue;
    @Autowired
    private ICustomApiKeyManager customApiKeyManager;
    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(apiKeyValue);
        if(apiKey == null || !customApiKeyManager.validateApiKey(apiKeyValue ,apiKey)){
            throw new BadCredentialsException("API Key invalida");
        } else {
            return new ApiKeyAuthentication(AuthorityUtils.NO_AUTHORITIES, apiKey);
        }
    }
}
