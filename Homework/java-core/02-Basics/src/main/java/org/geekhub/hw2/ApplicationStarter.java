package org.geekhub.hw2;

import java.util.Scanner;

public class ApplicationStarter {

    public static void main(String[] args) {
        if (args.length == 0 || Integer.parseInt(args[0]) < 0) {
            System.out.println("Please check the program's input arguments");
            System.exit(1);
        } else {
            int numOfStudents = Integer.parseInt(args[0]);

            String[] names = new String[numOfStudents];
            int numOfSubjects = 3;
            String[] subjects = new String[numOfSubjects];
            int[][] marks = new int[numOfStudents][numOfSubjects];

            Scanner scanner = new Scanner(System.in);

            for (int i = numOfStudents; i > 0; i--) {
                System.out.printf("Enter information for student #%s %n", numOfStudents - i + 1);

                System.out.print("Student's Name: ");
                String studentName = scanner.next();
                names[numOfStudents - i] = studentName;

                System.out.print("Math Grade (0-100): ");
                int mathMark = scanner.nextInt();
                marks[numOfStudents - i][0] = mathMark;
                subjects[0] = "Math: ";

                System.out.print("Science Grade (0-100): ");
                int scienceMark = scanner.nextInt();
                marks[numOfStudents - i][1] = scienceMark;
                subjects[1] = "Science: ";

                System.out.print("History Grade (0-100): ");
                int historyMark = scanner.nextInt();
                marks[numOfStudents - i][2] = historyMark;
                subjects[2] = "History: ";
            }
            scanner.close();
            studentGradesInfo(names, getStudentAverageMark(marks));
            subjectGradesInfo(subjects, getSubjectAverageMark(marks));
        }
    }

    public static void studentGradesInfo(String[] studentNames, double[] mark) {
        int index = mark.length;
        System.out.println("\n");
        System.out.println("Average Grades for Each Student:");

        for (String studentName : studentNames) {
            System.out.print(studentName + ": " + mark[index - studentNames.length] + "\n");
            index++;
        }
    }

    public static void subjectGradesInfo(String[] subjects, double[] mark) {
        int index = mark.length;
        System.out.println("\n");
        System.out.println("Average Grades for Each Subject:");

        for (String subject : subjects) {
            System.out.print(subject + mark[index - subjects.length] + "\n");
            index++;
        }
    }

    public static double[] getStudentAverageMark(int[][] allMarks) {
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

    public static double[] getSubjectAverageMark(int[][] allMarks) {
        double[] averageMark = new double[allMarks.length];

        for (int i = 0; i < allMarks.length; i++) {
            int sum = 0;
            int numStudents = allMarks[i].length;

            for (int j = 0; j < numStudents; j++) {
                sum += allMarks[j][i];
            }
            averageMark[i] = (double) sum / numStudents;
        }
        return averageMark;
    }

}
