package org.geekhub.learnit;

import org.geekhub.learnit.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationStarterTest {
    private static List<Student> students;
    private static Map<String, Double> scores;

    @BeforeEach
    void setUp() {
        students = new ArrayList<>();
        scores = new HashMap<>();
        scores.put("History", 50.5);
        scores.put("Math", 30.0);
    }

    @Test
    void gradesInput() {
    }

    @Test
    void getStudentInfo() {
    }

    @Test
    void getSpecificInfo() {
    }

    @Test
    void getAverageScore() {
    }
}
