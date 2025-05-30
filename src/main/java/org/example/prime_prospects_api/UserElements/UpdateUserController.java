package org.example.prime_prospects_api.UserElements;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.essence.TheUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateUserController {

    private final UserService userService;

    @PostMapping("/update/user")
    public ResponseEntity<String> update(@RequestBody UpdateRequest request) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
            }
            TheUser upUser = userService.getUsetByLogin(username);
            upUser.setCity(request.city);
            upUser.setPhoneNumber(request.phone_number);
            upUser.setEmail(request.email);
            upUser.setDescription(request.description);
            userService.saveUser(upUser);
            return new ResponseEntity<>("User information are updated", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    static class UpdateRequest {
        private String city;
        private String phone_number;
        private String email;
        private String description;
    }
}