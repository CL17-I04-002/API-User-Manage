package com.project.user.manage.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IAuthenticationService {
    Authentication getAuthentication(HttpServletRequest request);
}
