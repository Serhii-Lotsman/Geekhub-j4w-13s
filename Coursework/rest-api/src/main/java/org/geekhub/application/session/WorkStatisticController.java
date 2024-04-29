package org.geekhub.application.session;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.geekhub.application.converter.WorkSessionConverter;
import org.geekhub.application.session.dto.WorkStatisticDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v3/statistic")
@Tag(name = "tracking-work-statistic")
public class WorkStatisticController {

    private final WorkStatisticService workStatisticService;

    public WorkStatisticController(WorkStatisticService workStatisticService) {
        this.workStatisticService = workStatisticService;
    }

    @GetMapping("/hr-panel/all-work-sessions")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<Map<String, String>>> getAllMonthlyStatistic() {
        return workStatisticService.getAllSessionStatistic().stream()
            .map(WorkSessionConverter::workStatisticToDto)
            .collect(Collectors.groupingBy(
                WorkStatisticDto::date,
                Collectors.mapping(WorkSessionToMap::getAllSessionStatisticGroupByDate, Collectors.toList())
            ));
    }

    @GetMapping("/employee/my-work-session")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<Map<String, String>>> getMonthlyStatisticByEmail() {
        Map<String, List<Map<String, String>>> workStatisticDtoList = new LinkedHashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            workStatisticDtoList =
                workStatisticService.getWorkSessionStatisticByEmail(getEmail(authentication)).stream()
                    .map(WorkSessionConverter::workStatisticToDto)
                    .collect(Collectors.groupingBy(
                        WorkStatisticDto::userEmail,
                        Collectors.mapping(WorkSessionToMap::getUserSessionStatisticGroupByEmail, Collectors.toList())
                    ));
        }
        return workStatisticDtoList;
    }

    private String getEmail(Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }
}
