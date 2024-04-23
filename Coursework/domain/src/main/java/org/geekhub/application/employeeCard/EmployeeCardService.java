package org.geekhub.application.employeeCard;

import org.geekhub.application.exception.EmployeeCardException;
import org.geekhub.application.exception.UserException;
import org.geekhub.application.exception.UserExistException;
import org.geekhub.application.user.UserRepository;
import org.geekhub.application.validation.EmployeeValidation;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeCardService {


    private final EmployeeCardRepository employeeCardRepository;
    private final UserRepository userRepository;

    public EmployeeCardService(EmployeeCardRepository employeeCardRepository, UserRepository userRepository) {
        this.employeeCardRepository = employeeCardRepository;
        this.userRepository = userRepository;
    }

    public void saveEmployeeCard(EmployeeCardEntity employeeCardEntity) {
        if (employeeCardRepository.employeeEmailExist(employeeCardEntity.getEmail())) {
            throw new UserExistException("Email is already taken!");
        }

        if (!userRepository.existsByUserEmail(employeeCardEntity.getEmail())) {
            throw new UserException("Email not found");
        }

        employeeCardRepository.saveEmployeeCard(
            EmployeeValidation.validateFields(employeeCardEntity)
        );
    }

    public void deleteEmployeeCard(Long id) {
        getEmployeeCardById(id);
        employeeCardRepository.deleteEmployeeCard(id);
    }

    public EmployeeCardEntity getEmployeeCardById(Long id) {
        return employeeCardRepository.getEmployeeCard(id)
            .orElseThrow(() -> new EmployeeCardException("Employee card with id " + id + " not found"));
    }

    public EmployeeCardEntity getEmployeeCardByEmail(String email) {
        return employeeCardRepository.getEmployeeCard(email)
            .orElseThrow(() -> new EmployeeCardException("Employee card with email " + email + " not found"));
    }

    public List<EmployeeCardEntity> getAllEmployees() {
        return employeeCardRepository.getEmployeeCards();
    }

    public List<EmployeeCardEntity> getEmployeeCardsByCity(String city) {
        return employeeCardRepository.getEmployeeCards(city);
    }

    public List<EmployeeCardEntity> getEmployeeCardsByPosition(EmployeePosition employeePosition) {
        return employeeCardRepository.getEmployeeCards(employeePosition);
    }

    public List<EmployeeCardEntity> getEmployeeCardsByGender(EmployeeGender employeeGender) {
        return employeeCardRepository.getEmployeeCards(employeeGender);
    }
}

