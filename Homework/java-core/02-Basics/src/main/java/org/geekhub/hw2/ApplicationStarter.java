package org.geekhub.hw2;

import java.util.Scanner;

public class ApplicationStarter {

    public static void main(String[] args) {
        if (args.length == 0 || Integer.parseInt(args[0]) < 0) {
            System.out.println("Please check the program's input arguments");
            System.exit(1);
        } else {
            int numOfStudents = Integer.parseInt(args[0]);
            collectAndPrintAnalytics(numOfStudents);
        }
    }

    private static void collectAndPrintAnalytics(int numOfStudents) {
        String[] names = new String[numOfStudents];
        String[] subjectName = {"Math", "Science", "History"};
        int[][] grades = new int[numOfStudents][subjectName.length];

        Scanner scanner = new Scanner(System.in);

        for (int studentIndex = 0; studentIndex < numOfStudents; studentIndex++) {
            System.out.printf("Enter information for student #%d%n", studentIndex + 1);
            String studentName;
            do {
                System.out.print("Student's Name: ");
                studentName = scanner.useDelimiter("\\n").next();
            } while (validationInput(studentName.trim()));
            names[studentIndex] = studentName;

            for (int subjectIndex = 0; subjectIndex < subjectName.length; subjectIndex++) {
                int grade;
                do {
                    System.out.printf("%s Grade (0-100): ", subjectName[subjectIndex]);
                    grade = scanner.nextInt();
                } while (validationInput(grade));
                grades[studentIndex][subjectIndex] = grade;
            }
        }
        scanner.close();
        displayStudentAverageGrades(names, calculateAverage(grades));
        displaySubjectAverageGrades(subjectName, calculateSubjectAverage(grades));
    }

    private static boolean validationInput(String name) {
        if (name.isEmpty()) {
            System.out.printf("Please, enter the correct name, the name \"%s\" is not valid %n", name);
            return true;
        } else {
            return false;
        }
    }

    private static boolean validationInput(int mark) {
        if (mark >= 0 && mark <= 100) {
            return false;
        } else {
            System.out.printf("Please, enter the correct mark, the mark \"%s\" is not valid %n", mark);
            return true;
        }
    }

    private static void displayStudentAverageGrades(String[] studentNames, double[] mark) {
        int index = mark.length;
        System.out.println("\n");
        System.out.println("Average Grades for Each Student:");

        for (String studentName : studentNames) {
            System.out.print(studentName + ": " + mark[index - studentNames.length] + "\n");
            index++;
        }
    }

    private static void displaySubjectAverageGrades(String[] subjects, double[] mark) {
        int index = mark.length;
        System.out.println("\n");
        System.out.println("Average Grades for Each Subject:");

        for (String subject : subjects) {
            System.out.print(subject + ": " + mark[index - subjects.length] + "\n");
            index++;
        }
    }

    private static double[] calculateAverage(int[][] allMarks) {
        double[] averageMark = new double[allMarks.length];

        for (int i = 0; i < allMarks.length; i++) {
            int sum = 0;
            int numSubjects = allMarks[i].length;

            for (int j = 0; j < numSubjects; j++) {
                sum += allMarks[i][j];
            }
            averageMark[i] = (double) sum / numSubjects;
        }
        return averageMark;
    }

    private static double[] calculateSubjectAverage(int[][] allMarks) {
        double[] averageMarks = new double[allMarks[0].length];

        for (int i = 0; i < allMarks[0].length; i++) {
            int sum = 0;
            int numStudents = allMarks.length;

            for (int[] allMark : allMarks) {
                sum += allMark[i];
            }

            averageMarks[i] = (double) sum / numStudents;
        }
        return averageMarks;
    }

}
