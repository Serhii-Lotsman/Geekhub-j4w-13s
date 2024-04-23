package org.geekhub.application.employeeCard;

import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface EmployeeCardRepository {

    void saveEmployeeCard(EmployeeCardEntity employeeCardEntity);

    void deleteEmployeeCard(Long id);

    @NonNull
    Optional<EmployeeCardEntity> getEmployeeCard(Long id);

    @NonNull
    Optional<EmployeeCardEntity> getEmployeeCard(String email);

    @NonNull
    List<EmployeeCardEntity> getEmployeeCards();

    @NonNull
    List<EmployeeCardEntity> getEmployeeCards(String city);

    @NonNull
    List<EmployeeCardEntity> getEmployeeCards(EmployeePosition employeePosition);

    @NonNull
    List<EmployeeCardEntity> getEmployeeCards(EmployeeGender employeeGender);

    @NonNull
    List<EmployeeCardEntity> getEmployeeCards(int pageNum, int pageSize);

    @NonNull
    boolean employeeEmailExist(String email);

    @NonNull
    void updateEmployeeCard(EmployeeCardEntity employeeCardEntity);
}
