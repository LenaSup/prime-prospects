package org.example.prime_prospects_api.VacansyElements;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.UserElements.UserService;
import org.example.prime_prospects_api.essence.Vacancy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CreateVacansyController {
    private final UserService userService;
    private final VacansyService vacansyService;

    @PostMapping("/update/vacansy")
    public ResponseEntity<String> createVacansy(@RequestBody VRecvest request){
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long owner_id;
            if (principal instanceof UserDetails) {
                owner_id = userService.getIdByLogin(((UserDetails)principal).getUsername());
            } else {
                return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
            }
            Vacancy vacancy = new Vacancy();
            vacancy.setAddress(request.address);
            vacancy.setAdditionalSkills(request.additional_skill);
            vacancy.setEmploymentType(request.employmentType);
            vacancy.setWorkingHours(Integer.parseInt(request.workingHours));
            vacancy.setWorkExperienceYears(Integer.parseInt(request.workExperienceYears));
            vacancy.setWhatWeOffer(request.whatWeOffer);
            vacancy.setSchedule(request.schedule);
            vacancy.setSalary(Double.parseDouble(request.salary));
            vacancy.setRequirements(request.requirements);
            vacancy.setPosition(request.position);
            vacancy.setOvner_Id(owner_id);
            vacansyService.save(vacancy);
            return new ResponseEntity<>("vacansy has created", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    static class VRecvest{
        public String position;
        public String workExperienceYears;
        public String salary;
        public String requirements;
        public String whatWeOffer;
        public String additional_skill;
        public String employmentType;
        public String schedule;
        public String workingHours;
        public String address;

    }
}
