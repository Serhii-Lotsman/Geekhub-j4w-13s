package org.geekhub.application.session;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.converter.WorkSessionConverter;
import org.geekhub.application.session.dto.WorkSessionDto;
import org.geekhub.application.session.model.WorkSessionEntity;
import org.geekhub.application.validation.WorkSessionValidation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v3/work-session")
@Tag(name = "tracking-work-time")
public class WorkSessionController {

    private final WorkSessionService workSessionService;

    public WorkSessionController(WorkSessionService workSessionService) {
        this.workSessionService = workSessionService;
    }

    @GetMapping("/hr-panel/all-sessions")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<Map<String, Object>>> getAllSessions(
        @RequestParam(defaultValue = "1") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return workSessionService.getAllSessions(pageNum, pageSize).stream()
            .map(WorkSessionConverter::workSessionToDto)
            .collect(Collectors.groupingBy(
                WorkSessionDto::email,
                Collectors.mapping(WorkSessionToMap::getSessionDetailsGroupByEmail, Collectors.toList())
            ));
    }

    @GetMapping("/hr-panel/open-sessions")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkSessionDto> getAllOpenSessions(
        @RequestParam(defaultValue = "1") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return workSessionService.getAllOpenWorkSessions(pageNum, pageSize).stream()
            .map(WorkSessionConverter::workSessionToDto)
            .toList();
    }

    @GetMapping("/hr-panel/today-sessions")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkSessionDto> getAllTodaySessions(
        @RequestParam(defaultValue = "1") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return workSessionService.getAllTodaySessions(pageNum, pageSize).stream()
            .map(WorkSessionConverter::workSessionToDto)
            .toList();
    }

    @GetMapping("/employee/my-time")
    @ResponseStatus(HttpStatus.OK)
    public Map<LocalDate, List<Map<String, Object>>> getAllSessionsByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<LocalDate, List<Map<String, Object>>> workSessionEntityMap = new LinkedHashMap<>();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            workSessionEntityMap =
                workSessionService.getAllWorkSessionByEmail(getEmail(authentication)).stream()
                    .map(WorkSessionConverter::workSessionToDto)
                    .collect(Collectors.groupingBy(
                        WorkSessionDto::date,
                        Collectors.mapping(WorkSessionToMap::getSessionDetailsGroupByDate, Collectors.toList())
                    ));
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

    @PatchMapping("/employee/end-time")
    @ResponseStatus(HttpStatus.OK)
    public String endWorkDay() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            workSessionService.endWorkSession(getEmail(authentication));
        }

        return "Work time stopped";
    }

    @PatchMapping("/hr-panel/time-edit/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public String editWorkSession(
        @PathVariable long sessionId,
        @RequestParam byte hours,
        @RequestParam byte minutes
    ) {
        WorkSessionValidation.timeValidation(hours, minutes);

        WorkSessionEntity workSessionEntity = workSessionService.getOpenWorkSession(sessionId);
        LocalTime endTime = LocalTime.of(hours, minutes);
        workSessionEntity.setTimeEnd(endTime);
        workSessionService.editWorkSession(sessionId, workSessionEntity);

        return "Session time updated succeed";
    }

    private String getEmail(Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}