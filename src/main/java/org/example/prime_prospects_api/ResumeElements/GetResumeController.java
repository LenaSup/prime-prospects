package org.example.prime_prospects_api.ResumeElements;


import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.UserElements.UserService;
import org.example.prime_prospects_api.essence.Resume;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetResumeController {
    private final UserService userService;
    private final ResumeService resumeService;

    @GetMapping("/resume/private")
    public ResponseEntity<?> getResume(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long owner_id;
        if (principal instanceof UserDetails) {
            owner_id = userService.getIdByLogin(((UserDetails)principal).getUsername());
            StringBuilder json = new StringBuilder();
            json.append("{\"content\":[");
            List<Resume> outResumelist = resumeService.findByOwverId(owner_id);
            int s = outResumelist.size();
            int k = 0;
            for (int i = page * size; (i < (page + 1) * size) && (i < s); i++){
                if (i > page * size){
                    json.append(",");
                }
                k++;
                json.append(outResumelist.get(i).toJson());
            }
            json.append("],\"totalPages\":").append(page).append(",");
            json.append("\"totalElements\":").append(k).append("}");
            return ResponseEntity.ok(json.toString());
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/resume/private/short")
    public ResponseEntity<?> shortResume(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long owner_id;
        if (principal instanceof UserDetails) {
            owner_id = userService.getIdByLogin(((UserDetails)principal).getUsername());
            StringBuilder json = new StringBuilder();
            json.append("{\"content\":[");
            List<Long> ids = resumeService.getId(owner_id);
            List<String> positions = resumeService.getPosition(owner_id);
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
