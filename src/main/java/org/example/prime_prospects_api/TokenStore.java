package org.example.prime_prospects_api;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TokenStore {

    private final Map<String, String> tokens = new HashMap<>();

    public String generateToken(String username) {
        String token = UUID.randomUUID().toString();
        tokens.put(username, token);
        return token;
    }

    public String getToken(String username) {
        return tokens.get(username);
    }

    public void removeToken(String username) {
        tokens.remove(username);
    }

    public boolean isValidToken(String username, String token) {
        String storedToken = tokens.get(username);
        return storedToken != null && storedToken.equals(token);
    }

    public Map<String, String> getTokens() {
        return tokens;
    }
}

