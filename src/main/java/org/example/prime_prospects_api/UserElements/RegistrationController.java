package org.example.prime_prospects_api.UserElements;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        System.out.println(request);
        try {
            boolean buf = request.getIsEmployee() == "true";
            userService.registerUser(request.getName(), request.getLogin(), request.getPassword(), buf);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage() + request.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    static class RegistrationRequest {
        private String name;
        private String login;
        private String password;
        private String isEmployee;
    }
}
