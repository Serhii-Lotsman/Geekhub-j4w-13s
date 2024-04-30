package org.geekhub.application.employeeCard;

import org.geekhub.application.employeeCard.model.EmployeeCardEntity;
import org.geekhub.application.enums.EmployeeGender;
import org.geekhub.application.enums.EmployeePosition;
import org.geekhub.application.exception.EmployeeCardException;
import org.geekhub.application.exception.UserException;
import org.geekhub.application.exception.UserExistException;
import org.geekhub.application.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeCardServiceTest {

    private static final long EMPLOYEE_ID = 1L;
    @Mock
    private EmployeeCardRepository employeeCardRepository;

    @Mock
    private UserRepository userRepository;

    private EmployeeCardService employeeCardService;

    private final EmployeeCardEntity employeeCardEntity = new EmployeeCardEntity(
        "Firstname",
        "Lastname",
        LocalDate.of(2001, 1, 1),
        "test@gmail.com",
        EmployeePosition.ACCOUNTANT,
        "City",
        false,
        EmployeeGender.FEMALE,
        LocalDate.of(2024, 1, 1)
    );

    @BeforeEach
    void setUp() {
        employeeCardService = new EmployeeCardService(employeeCardRepository, userRepository);
    }

    @Test
    void testSaveEmployeeCard_shouldSaveCard_whenValidEmployee() {
        when(employeeCardRepository.employeeEmailExist("test@gmail.com")).thenReturn(false);
        when(userRepository.existsByUserEmail("test@gmail.com")).thenReturn(true);

        assertDoesNotThrow(() -> employeeCardService.saveEmployeeCard(employeeCardEntity));

        verify(employeeCardRepository, times(1)).saveEmployeeCard(employeeCardEntity);
    }

    @Test
    void testSaveEmployeeCard_shouldThrowUserExistException_whenEmailExists() {
        when(employeeCardRepository.employeeEmailExist("test@gmail.com")).thenReturn(true);

        assertThrows(UserExistException.class, () -> employeeCardService.saveEmployeeCard(employeeCardEntity));
        verify(employeeCardRepository, never()).saveEmployeeCard(any());
    }

    @Test
    void testSaveEmployeeCard_shouldThrowUserException_whenEmailNotFoundInUserRepository() {
        employeeCardEntity.setEmail("nonExistent@gmail.com");
        when(employeeCardRepository.employeeEmailExist("nonExistent@gmail.com")).thenReturn(false);
        when(userRepository.existsByUserEmail("nonExistent@gmail.com")).thenReturn(false);

        assertThrows(UserException.class, () -> employeeCardService.saveEmployeeCard(employeeCardEntity));
        verify(employeeCardRepository, never()).saveEmployeeCard(any());
    }

    @Test
    void testUpdateEmployeeCard_shouldUpdateEmployeeCard_whenValidEmployee() {
        EmployeeCardEntity updatedEmployeeCardEntity = this.employeeCardEntity;
        updatedEmployeeCardEntity.setFirstName("Updated");
        updatedEmployeeCardEntity.setLastName("Updated");
        assertDoesNotThrow(() -> employeeCardService.updateEmployeeCard(updatedEmployeeCardEntity));

        verify(employeeCardRepository, times(1)).updateEmployeeCard(employeeCardEntity);
    }

    @Test
    void testDeleteEmployeeCard_shouldDeleteCard_whenValidId() {
        when(employeeCardRepository.getEmployeeCard(EMPLOYEE_ID))
            .thenReturn(Optional.of(new EmployeeCardEntity()));

        assertDoesNotThrow(() -> employeeCardService.deleteEmployeeCard(EMPLOYEE_ID));
        verify(employeeCardRepository, times(1)).deleteEmployeeCard(EMPLOYEE_ID);
    }

    @Test
    void testDeleteEmployeeCard_shouldThrowEmployeeCardException_whenInvalidId() {
        Long employeeId = -EMPLOYEE_ID;
        when(employeeCardRepository.getEmployeeCard(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeCardException.class,
            () -> employeeCardService.deleteEmployeeCard(employeeId));
        verify(employeeCardRepository, never()).deleteEmployeeCard(employeeId);
    }

    @Test
    void testGetEmployeeCardById_shouldReturnEmployeeCard_whenValidId() {
        EmployeeCardEntity employeeCardEntity = new EmployeeCardEntity();
        when(employeeCardRepository.getEmployeeCard(EMPLOYEE_ID))
            .thenReturn(Optional.of(employeeCardEntity));

        EmployeeCardEntity result = employeeCardService.getEmployeeCardById(EMPLOYEE_ID);
        assertEquals(employeeCardEntity, result);
    }

    @Test
    void testGetEmployeeCardById_shouldThrowEmployeeCardException_whenInvalidId() {
        Long employeeId = -EMPLOYEE_ID;
        when(employeeCardRepository.getEmployeeCard(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeCardException.class,
            () -> employeeCardService.getEmployeeCardById(employeeId));
    }

    @Test
    void testGetEmployeeCardByEmail_shouldReturnEmployeeCardByEmail_whenValidEmail() {
        String email = "test@gmail.com";
        EmployeeCardEntity employeeCardEntity = new EmployeeCardEntity();
        when(employeeCardRepository.getEmployeeCard(email)).thenReturn(Optional.of(employeeCardEntity));

        EmployeeCardEntity result = employeeCardService.getEmployeeCardByEmail(email);
        assertEquals(employeeCardEntity, result);
    }

    @Test
    void testGetEmployeeCardByEmail_shouldThrowEmployeeCardException_whenInvalidEmail() {
        String email = "nonExistent@gmail.com";
        when(employeeCardRepository.getEmployeeCard(email)).thenReturn(Optional.empty());

        assertThrows(EmployeeCardException.class,
            () -> employeeCardService.getEmployeeCardByEmail(email));
    }

    @Test
    void testGetAllEmployees_shouldReturnListOfEmployeeCard_always() {
        int pageNum = 1;
        int pageSize = 10;
        List<EmployeeCardEntity> employeeList = Collections.emptyList();
        when(employeeCardRepository.getEmployeeCards(pageNum, pageSize)).thenReturn(employeeList);

        List<EmployeeCardEntity> result = employeeCardService.getAllEmployees(pageNum, pageSize);
        assertEquals(employeeList, result);
    }
}
