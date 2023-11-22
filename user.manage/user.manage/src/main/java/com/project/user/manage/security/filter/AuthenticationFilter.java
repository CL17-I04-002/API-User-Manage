package com.project.user.manage.security.filter;

import com.project.user.manage.service.AuthenticationService;
import com.project.user.manage.service.IAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationFilter extends GenericFilterBean {
    @Autowired
    private IAuthenticationService authenticationService;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if("POST".equals(httpRequest.getMethod()) && "/api/v1/serviceApiKey".equals(httpRequest.getRequestURI())){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            try {
                Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) servletRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                HttpServletResponse httptResponse = (HttpServletResponse) servletResponse;
                httptResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httptResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = httptResponse.getWriter();
                writer.println(e.getMessage());
                writer.flush();
                writer.close();
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
