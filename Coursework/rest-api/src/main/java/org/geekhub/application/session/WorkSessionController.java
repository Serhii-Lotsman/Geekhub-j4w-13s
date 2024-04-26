package org.geekhub.application.session;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v3/work-session")
@Tag(name = "tracking-work-time")
public class WorkSessionController {

    private final WorkSessionService workSessionService;

    public WorkSessionController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<Map<String, Object>>> getAllSessions() {
        return workSessionService.getAllSessions();
    }

    @GetMapping("/employee/my-time")
    @ResponseStatus(HttpStatus.OK)
    public Map<LocalDate, List<Map<String, Object>>> getAllSessionsByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<LocalDate, List<Map<String, Object>>> workSessionEntityMap = new LinkedHashMap<>();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            workSessionEntityMap = workSessionService.getAllWorkSessionByEmail(getEmail(authentication));
        }

        return workSessionEntityMap;
    }


    @PostMapping("/employee/start-time")
    @ResponseStatus(HttpStatus.CREATED)
    public String startWorkDay() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            workSessionService.beginWorkSession(getEmail(authentication));
        }
        return "Work time started";
    }

    @PostMapping("/employee/end-time")
    @ResponseStatus(HttpStatus.OK)
    public String endWorkDay() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            workSessionService.endWorkSession(getEmail(authentication));
        }
        return "Work time stopped";
    }

    private String getEmail(Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}
