package com.theater.app.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class Authority implements GrantedAuthority {
    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
