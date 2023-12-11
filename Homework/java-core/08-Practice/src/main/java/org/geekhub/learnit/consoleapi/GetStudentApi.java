package org.geekhub.learnit.consoleapi;

import org.geekhub.learnit.model.Student;
import org.geekhub.learnit.service.StudentsService;

import java.util.List;
import java.util.Scanner;

public class GetStudentApi {

    private final Scanner scanner;
    private final StudentsService studentsService;
    private static final String NO_STUDENT_FOUND = "No student found!";

    public GetStudentApi(Scanner scanner,
                         StudentsService studentsService) {
        this.scanner = scanner;
        this.studentsService = studentsService;
    }

    public void printLastCreatedStudent() {
        Student student = studentsService.getLastCreatedStudent();
        if (student == null) {
            System.out.println(NO_STUDENT_FOUND);
            return;
        }

        System.out.println("Last created student is " + student);
    }

    public void printSpecificStudent() {
        System.out.println("In which student you're interested in?");
        List<Student> students = studentsService.getStudents();
        for (int index = 0; index < students.size(); index++) {
            String name = students.get(index).name();
            System.out.println(index + " - " + name);
        }

        int index = Integer.parseInt(scanner.nextLine());

        Student student = studentsService.getStudent(index);
        if (student == null) {
            System.out.println(NO_STUDENT_FOUND);
            return;
        }

        System.out.println(student);
    }

    public void printAverageScores() {
        List<Student> students = studentsService.getStudents();
        if (students.isEmpty()) {
            System.out.println(NO_STUDENT_FOUND);
            return;
        }

        for (Student student : students) {
            double averageScore = student.getAverageScore();
            System.out.println("Student " + student.name() + " score is " + averageScore);
        }
    }
}
