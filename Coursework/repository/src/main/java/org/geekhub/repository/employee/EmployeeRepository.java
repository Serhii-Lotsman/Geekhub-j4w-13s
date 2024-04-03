package org.geekhub.repository.employee;

import org.geekhub.repository.employee.model.EmployeeEntity;
import org.geekhub.repository.employee.enums.EmployeeGender;
import org.geekhub.repository.employee.enums.EmployeePosition;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    void saveEmployee(EmployeeEntity employeeEntity);

    void deleteRecord(int id);

    @NonNull
    Optional<EmployeeEntity> getRecord(int id);

    @NonNull
    Optional<EmployeeEntity> getRecord(String email);

    @NonNull
    List<EmployeeEntity> getRecords();

    @NonNull
    List<EmployeeEntity> getRecords(String city);

    @NonNull
    List<EmployeeEntity> getRecords(EmployeePosition employeePosition);

    @NonNull
    List<EmployeeEntity> getRecords(EmployeeGender employeeGender);

    @NonNull
    List<EmployeeEntity> getRecords(int pageNum, int pageSize);
}
