package org.geekhub.crewcraft.repository;

import org.geekhub.crewcraft.EmployeeRecord;
import org.geekhub.crewcraft.model.GenderEnum;
import org.geekhub.crewcraft.model.PositionEnum;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface EmployeesRepository {
    void saveRecord(EmployeeRecord employeeRecord);

    void deleteRecord(int id);

    @NonNull
    Optional<EmployeeRecord> getRecord(int id);

    @NonNull
    Optional<EmployeeRecord> getRecord(String email);

    @NonNull
    List<EmployeeRecord> getRecords();

    @NonNull
    List<EmployeeRecord> getRecords(String city);

    @NonNull
    List<EmployeeRecord> getRecords(PositionEnum positionEnum);

    @NonNull
    List<EmployeeRecord> getRecords(GenderEnum genderEnum);

    @NonNull
    List<EmployeeRecord> getRecords(int pageNum, int pageSize);
}
