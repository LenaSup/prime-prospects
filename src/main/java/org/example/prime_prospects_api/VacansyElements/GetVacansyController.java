package org.example.prime_prospects_api.VacansyElements;

import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.UserElements.UserService;
import org.example.prime_prospects_api.essence.Vacancy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class GetVacansyController {
    private final UserService userService;
    private final VacansyService vacansyService;

    @GetMapping("/vacansy/private")
    public ResponseEntity<?> getVacansy(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long owner_id;
        if (principal instanceof UserDetails) {
            owner_id = userService.getIdByLogin(((UserDetails)principal).getUsername());
            StringBuilder json = new StringBuilder();
            json.append("{\"content\":[");
            List<Vacancy> outVacancylist = vacansyService.findByOwverId(owner_id);
            int s = outVacancylist.size();
            int k = 0;
            for (int i = page * size; (i < (page + 1) * size) && (i < s); i++){
                if (i > page * size){
                    json.append(",");
                }
                k++;
                json.append(outVacancylist.get(i).toJson());
            }
            json.append("],\"totalPages\":").append(page).append(",");
            json.append("\"totalElements\":").append(k).append("}");
            return ResponseEntity.ok(json.toString());
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/vacansy/private/short")
    public ResponseEntity<?> shortResume(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long owner_id;
        if (principal instanceof UserDetails) {
            owner_id = userService.getIdByLogin(((UserDetails)principal).getUsername());
            StringBuilder json = new StringBuilder();
            json.append("{\"content\":[");
            List<Long> ids = vacansyService.getId(owner_id);
            List<String> positions = vacansyService.getPosition(owner_id);
            int size = ids.size();
            for (int i = 0; i < size; i++){
                String s = "{\"id\":\"" + ids.get(i) + "\",\"position\":\"" + positions.get(i) + "\"},";
                json.append(s);
            }
            json.append("],");
            json.append("\"size\":").append(size).append("}");
            return ResponseEntity.ok(json.toString());
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }
}
