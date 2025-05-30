package org.example.prime_prospects_api;

import java.io.Serializable;

public class AuthTokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    public AuthTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
