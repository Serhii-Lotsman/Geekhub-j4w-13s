package org.geekhub.application.employeeCard;

import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface EmployeeCardRepository {
    void saveEmployee(EmployeeCardEntity employeeCardEntity);

    void deleteRecord(int id);

    @NonNull
    Optional<EmployeeCardEntity> getRecord(int id);

    @NonNull
    Optional<EmployeeCardEntity> getRecord(String email);

    @NonNull
    List<EmployeeCardEntity> getRecords();

    @NonNull
    List<EmployeeCardEntity> getRecords(String city);

    @NonNull
    List<EmployeeCardEntity> getRecords(EmployeePosition employeePosition);

    @NonNull
    List<EmployeeCardEntity> getRecords(EmployeeGender employeeGender);

    @NonNull
    List<EmployeeCardEntity> getRecords(int pageNum, int pageSize);
}
