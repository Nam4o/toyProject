package com.nam4o.myweb.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtility {
    public static String getCurrentUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("인증 정보가 없습니다.");
        }
        return authentication.getName();
    }
}
