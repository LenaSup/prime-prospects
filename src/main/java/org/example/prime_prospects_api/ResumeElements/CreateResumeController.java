package org.example.prime_prospects_api.ResumeElements;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.UserElements.UserService;
import org.example.prime_prospects_api.essence.Resume;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CreateResumeController {
    private final UserService userService;
    private final ResumeService resumeService;

    @PostMapping("/update/resume")
    public ResponseEntity<String> createUser(@RequestBody CreateRequest request) {

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long owner_id;
            if (principal instanceof UserDetails) {
                owner_id = userService.getIdByLogin(((UserDetails)principal).getUsername());
            } else {
                return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
            }
            Resume resume = new Resume();
            resume.setAboutMe(request.aboutMe);
            resume.setAdditionalSkills(request.additionalSkills);
            resume.setExpectedSalary(Double.parseDouble(request.expectedSalary));
            resume.setPosition(request.position);
            resume.setWorkYears(Integer.parseInt(request.workYears));
            resume.setOvner_Id(owner_id);
            resume.setSkills(request.skills);
            resumeService.save(resume);
            return new ResponseEntity<>("resume has created", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    static class CreateRequest {
        private String position;
        private String workYears;
        private String expectedSalary;
        private String skills;
        private String aboutMe;
        private String additionalSkills;
    }
}
