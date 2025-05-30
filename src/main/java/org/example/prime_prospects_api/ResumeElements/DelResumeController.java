package org.example.prime_prospects_api.ResumeElements;


import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.essence.Resume;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DelResumeController {
    private final ResumeService resumeService;

    @DeleteMapping("/del/resume")
    public ResponseEntity<?> delitResume(@RequestParam long id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                resumeService.del(resumeService.find(id));
                return new ResponseEntity<>("resume wos remowed", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }
}
