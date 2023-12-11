package org.geekhub.learnit;

import org.geekhub.learnit.model.Student;
import org.geekhub.learnit.repository.StudentRepository;
import org.geekhub.learnit.service.StudentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentsServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentsService studentsService;

    @BeforeEach
    void setUp() {
        studentsService = new StudentsService(studentRepository);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void createStudent_shouldThrowIllegalArgumentException_whenNameIsInvalid(String name) {
        Executable executable = () -> studentsService.createStudent(name, emptyMap());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    void getLastCreatedStudent_shouldReturnLastUser_always() {
        Student expectedStudent = new Student("name3", emptyMap());
        List<Student> students = List.of(
            new Student("name1", Collections.emptyMap()),
            new Student("name2", Collections.emptyMap()),
            expectedStudent
        );
        when(studentRepository.getStudents()).thenReturn(students);

        Student lastCreatedStudent = studentsService.getLastCreatedStudent();

        assertEquals(expectedStudent, lastCreatedStudent);
    }

    @Test
    void getLastCreatedStudent_shouldThrowIllegalStateException_whenStudentListIsEmpty() {
        when(studentRepository.getStudents()).thenReturn(Collections.emptyList());

        Executable executable = () -> studentsService.getLastCreatedStudent();

        assertThrows(IllegalStateException.class, executable);
    }

    @Test
    void createStudent_shouldThrowIllegalArgumentException_whenScoresIsEmpty() {
        Executable executable = () -> studentsService.createStudent("testName", emptyMap());

        assertThrows(IllegalArgumentException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void createStudent_shouldThrowIllegalArgumentException_whenSubjectIsEmpty(String subject) {
        Executable executable = () -> studentsService.createStudent("testName", Map.of(subject, 20.0));

        assertThrows(IllegalArgumentException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.0001, 100.0001})
    void createStudent_shouldThrowIllegalArgumentException_whenScoresInRange(double score) {
        Executable executable = () -> studentsService.createStudent("testName", Map.of("Subject", score));


        assertThrows(IllegalArgumentException.class, executable);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void getStudent_shouldReturnUserByIndex_always(int index) {
        List<Student> students = List.of(
            new Student("name1", Collections.emptyMap()),
            new Student("name2", Collections.emptyMap()),
            new Student("name3", Collections.emptyMap())
        );
        when(studentRepository.getStudent(index)).thenReturn(students.get(index));

        Student studentByIndex = studentsService.getStudent(index);

        assertEquals(students.get(index), studentByIndex);
    }

    @Test
    void getStudents_shouldReturnListOfStudents_always() {
        List<Student> students = List.of(
            new Student("name1", Collections.emptyMap()),
            new Student("name2", Collections.emptyMap())
        );
        when(studentRepository.getStudents()).thenReturn(students);

        assertEquals(students, studentsService.getStudents());
    }

}
