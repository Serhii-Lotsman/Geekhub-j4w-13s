package org.geekhub.crewcraft.repository;

import org.geekhub.crewcraft.model.EmployeeEntity;
import org.geekhub.crewcraft.enums.EmployeeGender;
import org.geekhub.crewcraft.enums.EmployeePosition;
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
