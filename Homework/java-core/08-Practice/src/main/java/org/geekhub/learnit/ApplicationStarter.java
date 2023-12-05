package org.geekhub.learnit;

import org.geekhub.learnit.exception.StudentException;
import org.geekhub.learnit.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class ApplicationStarter {

    private static final List<Student> students = new ArrayList<>();
    private static Exception exception = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Student student = null;
        Map<String, Double> scores = null;
        boolean isNotExit = true;
        while (isNotExit) {
            System.out.println("Enter option: ");
            System.out.println("create");
            System.out.println("get-last-created-student-scores");
            System.out.println("get-specific-student-info");
            System.out.println("average");
            System.out.println("exit");
            switch (scanner.nextLine()) {
                case "create":
                    if (scores == null) {
                        scores = new HashMap<>();
                    } else {
                        scores.entrySet().removeIf(entry -> true);
                    }
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    if (name.isBlank()) {
                        System.out.println("Name cannot be empty");
                        break;
                    }
                    gradesInput(scores);

                    student = new Student(name, new HashMap<>(scores));
                    students.add(student);
                    exception = null;
                    break;
                case "get-last-created-student-scores":
                    try {
                        getStudentInfo(student, scores);
                    } catch (Exception e) {
                        isNotExit = false;
                        System.out.println("Error!");
                    }
                    break;
                case "get-specific-student-info":
                    try {
                        getSpecificInfo();
                    } catch (Exception e) {
                        isNotExit = false;
                        System.out.println("Error!");
                    }
                    exception = null;
                    break;
                case "average":
                    if (!students.isEmpty()) {
                        getAverageScore();
                        break;
                    }

                    System.out.println("No student found!");
                    break;
                case "exit":
                    isNotExit = false;
                default:
                    System.out.println("Ooops! Something went wrong :(");

            }
        }
        scanner.close();
    }

    static void gradesInput(Map<String, Double> scores) {
        while (exception == null) {
            try {
                System.out.println("Enter subject. Empty if exit");
                String subjectName = scanner.nextLine();
                if (subjectName.isEmpty()) {
                    throw new StudentException("Empty subject name.");
                } else {
                    System.out.println("Please enter from 0-100 to set " + subjectName + " score.");
                    double subjectScore = Double.parseDouble(scanner.nextLine());
                    if (subjectScore > 0 && subjectScore <= 100) {
                        scores.put(subjectName, subjectScore);
                    } else {
                        throw new StudentException("Score is less than 0 or higher than 100");
                    }
                }
            } catch (Exception e) {
                exception = e;
            }
        }
    }

    static void getStudentInfo(Student student, Map<String, Double> scores) {
        if (student != null) {
            StringBuilder info = new StringBuilder().append("Student: ").append(student.name()).append(" scores: ");
            for (Map.Entry marks : scores.entrySet()) {
                info.append("\nSubject: ").append(marks.getKey()).append(" scores: ").append(marks.getValue());
            }
            System.out.println(info);
        }
    }

    static void getSpecificInfo() {
        System.out.println("In which student you're interested in?");
        int studentIndex = 0;
        for (Student disciple : students) {
            String string = "Student: " + disciple.name() + " index: " + studentIndex++;
            System.out.println(string);
        }

        System.out.println("Enter index:");
        int getIndex = Integer.parseInt(scanner.nextLine());
        Student studentInfo = students.get(getIndex);
        if (studentInfo != null) {
            StringBuilder string = new StringBuilder().append("Student: ").append(studentInfo.name()).append(" scores: ");
            for (Map.Entry marks : studentInfo.scores().entrySet()) {
                string.append("\nSubject :").append(marks.getKey()).append(" scores: ").append(marks.getValue());
            }
            System.out.println(string);
        }
    }

    static void getAverageScore() {
        for (Student discipleInfo : students) {
            Map<String, Double> scores3 = discipleInfo.scores();

            double averageScore = scores3.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
            System.out.println("Student " + discipleInfo.name() + " score is " + averageScore);
        }
    }
}
