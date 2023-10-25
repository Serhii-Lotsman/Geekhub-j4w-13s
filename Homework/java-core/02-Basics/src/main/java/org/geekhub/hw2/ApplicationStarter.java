package org.geekhub.hw2;

import java.util.Scanner;

public class ApplicationStarter {

    public static void main(String[] args) {
        int numStudents = Integer.parseInt(args[0]);

        if (numStudents <= 0) {
            exit();
        } else {
            String[] names = new String[numStudents];

            Scanner scanner = new Scanner(System.in);

            for (int i = numStudents; i > 0; i--) {
                System.out.printf("Enter information for student %s %n", numStudents - i + 1);
                System.out.println("Student's Name: ");
                String studentName = scanner.nextLine();
                names[numStudents - i] = studentName;
            }
            scanner.close();
            studentInfo(names);
        }
    }

    public static void exit() {
        System.out.println("Something went wrong.\n"
            + "Please enter the correct number in the input parameters of the application");
    }

    public static void studentInfo(String[] students) {
        for (String student : students) {
            System.out.println(student);
        }
    }

}
