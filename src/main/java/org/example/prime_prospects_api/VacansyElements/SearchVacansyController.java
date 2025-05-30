package org.example.prime_prospects_api.VacansyElements;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.UserElements.UserService;
import org.example.prime_prospects_api.essence.TheUser;
import org.example.prime_prospects_api.essence.Vacancy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchVacansyController {
    final private VacansyService vacansyService;
    final private UserService userService;

    @PostMapping("/search/filter/vacansy")
    public ResponseEntity<?> searchVF(@RequestBody Filters request,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                List<Vacancy> Vacansylist = vacansyService.search(request.getPosition(),
                        request.getCity(),
                        request.getEmploymentType(),
                        request.getSchedule(),
                        Integer.parseInt(request.getWorkYears()),
                        Integer.parseInt(request.getWorkingHours()),
                        Double.parseDouble(request.getSalary()));
                StringBuilder json = new StringBuilder();
                json.append("{\"content\":[");
                int s = Vacansylist.size();
                int k = 0;
                for (int i = page * size; (k < size) && (i < s); i++){
                    Vacancy vacancy = Vacansylist.get(i);
                    TheUser owner = userService.getUsetById(vacancy.getOvner_Id());
                    k++;
                    json.append("{ \"id\":\"" + vacancy.getId() + "\",");
                    json.append("\"position\":\"" + vacancy.getPosition() + "\",");
                    json.append("\"workExperienceYears\":\"" + vacancy.getWorkExperienceYears() + "\",");
                    json.append("\"salary\":\"" + vacancy.getSalary() + "\",");
                    json.append("\"requirements\":\"" + vacancy.getRequirements() + "\",");
                    json.append("\"whatWeOffer\":\"" + vacancy.getWhatWeOffer() + "\",");
                    json.append("\"additionalSkills\":\"" + vacancy.getAdditionalSkills() + "\",");
                    json.append("\"owner\":\"" + owner.getName() + "\",");
                    json.append("\"contactsOwner\":\"" + owner.getEmail() + " " + owner.getPhoneNumber() + "\",");
                    json.append("\"owner_description\":\"" + owner.getDescription() + "\" } ,");

                    json.append("\"employmentType\":\"" + vacancy.getEmploymentType() + "\",");
                    json.append("\"schedule\":\"" + vacancy.getSchedule() + "\",");
                    json.append("\"workingHours\":\"" + vacancy.getWorkingHours() + " " + owner.getPhoneNumber() + "\",");
                    json.append("\"address\":\"" + vacancy.getAddress() + "\" } ,");

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
        private String salary;
        private String workYears;
        private String city;
        private String workingHours;
        private String employmentType;
        private String schedule;
    }

    @GetMapping("/search/position/vacansy")
    public ResponseEntity<?> searchVP(@RequestParam String position,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                List<Vacancy> Vacansylist = vacansyService.searchByPosition(position);
                StringBuilder json = new StringBuilder();
                json.append("{\"content\":[");
                int s = Vacansylist.size();
                int k = 0;
                for (int i = page * size; (k < size) && (i < s); i++){
                    Vacancy vacancy = Vacansylist.get(i);
                    TheUser owner = userService.getUsetById(vacancy.getOvner_Id());
                    k++;
                    json.append("{ \"id\":\"" + vacancy.getId() + "\",");
                    json.append("\"position\":\"" + vacancy.getPosition() + "\",");
                    json.append("\"workExperienceYears\":\"" + vacancy.getWorkExperienceYears() + "\",");
                    json.append("\"salary\":\"" + vacancy.getSalary() + "\",");
                    json.append("\"requirements\":\"" + vacancy.getRequirements() + "\",");
                    json.append("\"whatWeOffer\":\"" + vacancy.getWhatWeOffer() + "\",");
                    json.append("\"additionalSkills\":\"" + vacancy.getAdditionalSkills() + "\",");
                    json.append("\"owner\":\"" + owner.getName() + "\",");
                    json.append("\"contactsOwner\":\"" + owner.getEmail() + " " + owner.getPhoneNumber() + "\",");
                    json.append("\"owner_description\":\"" + owner.getDescription() + "\" } ,");

                    json.append("\"employmentType\":\"" + vacancy.getEmploymentType() + "\",");
                    json.append("\"schedule\":\"" + vacancy.getSchedule() + "\",");
                    json.append("\"workingHours\":\"" + vacancy.getWorkingHours() + " " + owner.getPhoneNumber() + "\",");
                    json.append("\"address\":\"" + vacancy.getAddress() + "\" } ,");

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
