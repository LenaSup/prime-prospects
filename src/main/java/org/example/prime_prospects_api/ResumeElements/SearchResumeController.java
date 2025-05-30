package org.example.prime_prospects_api.ResumeElements;


import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.UserElements.UserService;
import org.example.prime_prospects_api.essence.Resume;
import org.example.prime_prospects_api.essence.TheUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchResumeController {
    private final UserService userService;
    private final ResumeService resumeService;

    @PostMapping("/search/filter/resume")
    public ResponseEntity<?> searchFilter(@RequestBody Filters request,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "15") int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {

            try {

                List<Resume> outResumelist = resumeService.search(
                        request.position,
                        request.skills,
                        Integer.parseInt(request.workYears),
                        Double.parseDouble(request.expectedSalary));
                StringBuilder json = new StringBuilder();
                json.append("{\"content\":[");
                int s = outResumelist.size();
                int k = 0;
                for (int i = page * size; (k < size) && (i < s); i++){
                    Resume resume = outResumelist.get(i);
                    TheUser owner = userService.getUsetById(resume.getOvner_Id());
                    if (owner.getCity().contains(request.cite)){
                        k++;
                        json.append("{ \"id\":\"" + resume.getId() + "\",");
                        json.append("\"position\":\"" + resume.getPosition() + "\",");
                        json.append("\"workYears\":\"" + resume.getWorkYears() + "\",");
                        json.append("\"expectedSalary\":\"" + resume.getExpectedSalary() + "\",");
                        json.append("\"skills\":\"" + resume.getSkills() + "\",");
                        json.append("\"aboutMe\":\"" + resume.getAboutMe() + "\",");
                        json.append("\"additionalSkills\":\"" + resume.getAdditionalSkills() + "\",");
                        json.append("\"name\":\"" + owner.getName() + "\",");
                        json.append("\"contactsOwner\":\"" + owner.getEmail() + " " + owner.getPhoneNumber() + "\",");
                        json.append("\"site\":\"" + owner.getCity() + "\" } ,");
                    }
                }
                json.append("],\"totalPages\":").append(page).append(",");
                json.append("\"totalElements\":").append(k).append("}");
                return ResponseEntity.ok(json.toString());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    static class Filters{
        private String position;
        private String expectedSalary;
        private String cite;
        private String workYears;
        private String skills;
    }

    @GetMapping("/search/position/resume")
    public ResponseEntity<?> searchPosition(@RequestParam String position,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "15") int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {

            try {

                List<Resume> outResumelist = resumeService.searchByPosition(position);
                StringBuilder json = new StringBuilder();
                json.append("{\"content\":[");
                int s = outResumelist.size();
                int k = 0;
                for (int i = page * size; (k < size) && (i < s); i++){
                    Resume resume = outResumelist.get(i);
                    TheUser owner = userService.getUsetById(resume.getOvner_Id());
                    json.append("{ \"id\":\"" + resume.getId() + "\",");
                    json.append("\"position\":\"" + resume.getPosition() + "\",");
                    json.append("\"workYears\":\"" + resume.getWorkYears() + "\",");
                    json.append("\"expectedSalary\":\"" + resume.getExpectedSalary() + "\",");
                    json.append("\"skills\":\"" + resume.getSkills() + "\",");
                    json.append("\"aboutMe\":\"" + resume.getAboutMe() + "\",");
                    json.append("\"additionalSkills\":\"" + resume.getAdditionalSkills() + "\",");
                    json.append("\"name\":\"" + owner.getName() + "\",");
                    json.append("\"contactsOwner\":\"" + owner.getEmail() + " " + owner.getPhoneNumber() + "\",");
                    json.append("\"site\":\"" + owner.getCity() + "\" } ,");
                    k++;
                }
                json.append("],\"totalPages\":").append(page).append(",");
                json.append("\"totalElements\":").append(k).append("}");
                return ResponseEntity.ok(json.toString());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }
}
