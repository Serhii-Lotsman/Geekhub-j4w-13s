package org.geekhub.application.session;

import org.geekhub.application.employeeCard.EmployeeCardRepository;
import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.geekhub.application.enums.EmployeePosition;
import org.geekhub.application.exception.EmployeeCardException;
import org.geekhub.application.exception.SessionException;
import org.geekhub.application.session.model.SalaryEntity;
import org.geekhub.application.session.model.WorkSessionEntity;
import org.geekhub.application.validation.WorkSessionValidation;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;
    private final EmployeeCardRepository employeeCardRepository;

    public WorkSessionService(
        WorkSessionRepository workSessionRepository,
        EmployeeCardRepository employeeCardRepository
    ) {
        this.workSessionRepository = workSessionRepository;
        this.employeeCardRepository = employeeCardRepository;
    }

    public void beginWorkSession(String email) {
        if (workSessionRepository.existsStartedTimeByUserEmailOnToday(email)) {
            throw new SessionException("The working day has already started");
        }

        if (!employeeCardRepository.employeeEmailExist(email)) {
            throw new EmployeeCardException("Failed to start time. Need to create employee card");
        }

        workSessionRepository.createWorkSession(email);
    }

    public void endWorkSession(String email) {
        WorkSessionEntity workSessionEntity = getTodayWorkSessionByEmail(email);
        EmployeeCardEntity employeeCardEntity = employeeCardRepository.getEmployeeCard(email)
            .orElseThrow(() -> new EmployeeCardException("No available employee card"));

        if (workSessionEntity.getTimeEnd() != null) {
            throw new SessionException("The working day doesn't started");
        }

        LocalTime totalTime = calculateTotalTime(
            workSessionEntity.getTimeBegin(),
            LocalTime.now(ZoneId.of("UTC"))
        );

        SalaryEntity salaryEntity = countSalary(totalTime, employeeCardEntity.getEmployeePosition());
        workSessionRepository.closeWorkSession(workSessionEntity.getId(),
            LocalTime.now(ZoneId.of("UTC")),
            totalTime,
            salaryEntity.getSalary()
        );

    }

    public List<WorkSessionEntity> getAllSessions(int pageNum, int pageSize) {
        return workSessionRepository.findAllWorkSessions(pageNum, pageSize);
    }

    public WorkSessionEntity getTodayWorkSessionByEmail(String email) {
        return workSessionRepository.findTodayWorkSessionByEmail(email)
            .orElseThrow(() -> new SessionException("Failed to find session for today"));
    }

    public List<WorkSessionEntity> getAllWorkSessionByEmail(String email) {
        return workSessionRepository.findAllWorkSessionByEmail(email);
    }

    public List<WorkSessionEntity> getAllTodaySessions(int pageNum, int pageSize) {
        return workSessionRepository.findAllTodaySessions(pageNum, pageSize);
    }

    public void editWorkSession(long sessionId, WorkSessionEntity workSessionEntity) {
        EmployeeCardEntity employeeCardEntity =
            employeeCardRepository.getEmployeeCard(workSessionEntity.getEmail())
            .orElseThrow(() -> new EmployeeCardException("No available employee card"));

        LocalTime totalTime = calculateTotalTime(
            workSessionEntity.getTimeBegin(),
            workSessionEntity.getTimeEnd()
        );
        workSessionEntity.setTotalTime(totalTime);
        SalaryEntity salaryEntity = countSalary(totalTime, employeeCardEntity.getEmployeePosition());
        workSessionRepository.editOpenWorkSession(sessionId, workSessionEntity, salaryEntity.getSalary());
    }

    public WorkSessionEntity getOpenWorkSession(long sessionId) {
        return workSessionRepository.findOpenWorkSessionBySessionId(sessionId)
            .orElseThrow(() -> new SessionException("Failed to find work session by id " + sessionId));
    }

    public List<WorkSessionEntity> getAllOpenWorkSessions(int pageNum, int pageSize) {
        return workSessionRepository.findAllOpenWorkSessions(pageNum, pageSize);
    }

    private LocalTime calculateTotalTime(LocalTime timeBegin, LocalTime timeEnd) {
        WorkSessionValidation.sequenceOfTimeValidation(timeBegin, timeEnd);
        Duration duration = Duration.between(timeBegin, timeEnd);
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return LocalTime.of((int) hours, (int) minutes);
    }

    private SalaryEntity countSalary(LocalTime totalTime, EmployeePosition employeePosition) {
        SalaryEntity salaryEntity = new SalaryEntity();
        salaryEntity.setRatePerHour(employeePosition);

        double ratePerHour = salaryEntity.getRatePerHour();
        long totalMinutes = totalTime.toSecondOfDay() / 60;
        double salary = ratePerHour * totalMinutes / 60;

        salaryEntity.setSalary(salary);
        return salaryEntity;
    }
}
