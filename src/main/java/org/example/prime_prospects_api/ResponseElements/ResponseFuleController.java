package org.example.prime_prospects_api.ResponseElements;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.prime_prospects_api.UserElements.UserService;
import org.example.prime_prospects_api.essence.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResponseFuleController {
    final private UserService userService;
    final private ResponseService responseService;

    @GetMapping("/response/accepted")
    public ResponseEntity<?> accepted(@RequestParam long id,
                                      @RequestParam boolean  decision){
        try {
            responseService.set(id, decision);
            return new ResponseEntity<>("decision "+ decision + " to " + id , HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/response/create")
    public ResponseEntity<?> createResponse(@RequestBody RestonseData request){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
                Response response = new Response();
                response.setIn(Long.parseLong(request.in_id));
                response.setOut(Long.parseLong(request.out_id));
                response.setStatus(false);
                response.setApplicationDate(LocalDateTime.now());
                responseService.sive(response);
                return new ResponseEntity<>("response created", HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/response")
    public ResponseEntity<?> getResume(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "true") boolean me) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id;
        if (principal instanceof UserDetails) {
            id = userService.getIdByLogin(((UserDetails)principal).getUsername());
            StringBuilder json = new StringBuilder();
            json.append("{\"content\":[");
            List<Response> listResp;
            if (me){
                listResp = responseService.getIn(id);
            }
            else {
                listResp = responseService.getOut(id);
            }
            int s = listResp.size();
            int k = 0;
            for (int i = page * size; (i < (page + 1) * size) && (i < s); i++){
                k++;
                json.append(listResp.get(i).toJson());
                json.append(",");
            }
            json.append("],\"totalPages\":").append(page).append(",");
            json.append("\"totalElements\":").append(k).append("}");
            return ResponseEntity.ok(json.toString());
        } else {
            return new ResponseEntity<>("find user error", HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    static class RestonseData{
        private String in_id;
        private String out_id;
    }
}
