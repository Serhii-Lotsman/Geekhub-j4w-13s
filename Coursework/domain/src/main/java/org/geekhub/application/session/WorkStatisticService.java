package org.geekhub.application.session;

import org.geekhub.application.session.model.WorkStatisticEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkStatisticService {

    private final WorkStatisticRepository workStatisticRepository;

    public WorkStatisticService(WorkStatisticRepository workStatisticRepository) {
        this.workStatisticRepository = workStatisticRepository;
    }

    public List<WorkStatisticEntity> getAllSessionStatistic() {
        return workStatisticRepository.findAllMonthlyStatistic();
    }

    public List<WorkStatisticEntity> getWorkSessionStatisticByEmail(String email) {
        return workStatisticRepository.findUserMonthlyStatisticByEmail(email);
    }
}
