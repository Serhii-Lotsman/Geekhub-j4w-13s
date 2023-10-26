package org.geekhub.hw2;

import java.util.Scanner;

public class ApplicationStarter {

    public static void main(String[] args) {
        int numOfStudents = Integer.parseInt(args[0]);

        if (numOfStudents <= 0) {
            System.out.println("Please, check input arguments of the application");
            System.exit(1);
        } else {
            String[] names = new String[numOfStudents];
            int numOfSubjects = 3;
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

                System.out.print("Science Grade (0-100): ");
                int scienceMark = scanner.nextInt();
                marks[numOfStudents - i][1] = scienceMark;

                System.out.print("History Grade (0-100): ");
                int historyMark = scanner.nextInt();
                marks[numOfStudents - i][2] = historyMark;
            }
            scanner.close();
            studentGradesInfo(names, getStudentAverageMark(marks));
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

}
