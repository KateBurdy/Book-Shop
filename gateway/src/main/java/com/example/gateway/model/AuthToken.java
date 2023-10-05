package com.example.gateway.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;
import java.util.Objects;

public class AuthToken extends AbstractAuthenticationToken {

    private final String token;

    public AuthToken(String token) {
        super(Collections.emptyList());
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }


    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Create a new Authentication object to authenticate");
        }
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(token, authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token);
    }
}
