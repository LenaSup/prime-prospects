package org.example.prime_prospects_api.UserElements;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.AuthTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        try {
            AuthTokenResponse token = userService.login(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
            return new ResponseEntity<>(token.getToken(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    @Data
    static class LoginRequest {
        private String usernameOrEmail;
        private String password;
    }
}
