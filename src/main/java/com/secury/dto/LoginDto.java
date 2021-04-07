package com.secury.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginDto extends User {

    private static final long serialVersionUID = 1L;

    public LoginDto(String fullname, String password,
                    Collection<? extends GrantedAuthority> authorities) {
        super(fullname, password, authorities);
    }
}