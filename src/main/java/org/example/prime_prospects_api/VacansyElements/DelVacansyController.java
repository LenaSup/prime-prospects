package org.example.prime_prospects_api.VacansyElements;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DelVacansyController {
    private final VacansyService vacansyService;

    @DeleteMapping("/del/vacansy")
    public ResponseEntity<?> delitVacansy(@RequestParam long id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                vacansyService.del(vacansyService.find(id));
                return new ResponseEntity<>("resume wos remowed", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }
}
