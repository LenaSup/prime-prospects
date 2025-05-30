package org.example.prime_prospects_api.UserElements;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetUserFieldController {
    private final UserService userService;

    @GetMapping("/user/field/{field}")
    public ResponseEntity<?> getUserField(@PathVariable String field) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        try {
            AllowedUserFields allowedField = AllowedUserFields.valueOf(field.toUpperCase());
            Object fieldValue = userService.getUserField(username, allowedField);
            return ResponseEntity.ok(fieldValue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid field: " + field);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting user field");
        }
    }
}
