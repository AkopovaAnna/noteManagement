package com.project.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAccessor {

    private SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

    void setAuthentication(Authentication authentication) {
        getContext().setAuthentication(authentication);
    }

    Authentication getAuthentication() {
        return getContext().getAuthentication();
    }

    public UserDetails getUserDetails() {
        final Authentication authentication = getContext().getAuthentication();
        if(authentication != null && authentication.getDetails() instanceof UserDetails) {
            return (UserDetails) authentication.getDetails();
        }
        return null;
    }
}
