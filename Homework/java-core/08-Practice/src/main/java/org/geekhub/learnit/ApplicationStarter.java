package org.geekhub.learnit;

import org.geekhub.learnit.consoleapi.CreateStudentApi;
import org.geekhub.learnit.consoleapi.GetStudentApi;
import org.geekhub.learnit.consoleapi.MainMenu;
import org.geekhub.learnit.repository.StudentRepository;
import org.geekhub.learnit.repository.StudentRepositoryInMemory;
import org.geekhub.learnit.service.StudentsService;

import java.util.Scanner;


public class ApplicationStarter {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            StudentRepository studentRepository = new StudentRepositoryInMemory();
            StudentsService studentsService = new StudentsService(studentRepository);
            CreateStudentApi createStudentApi = new CreateStudentApi(scanner, studentsService);
            GetStudentApi getStudentApi = new GetStudentApi(scanner, studentsService);

            MainMenu mainMenu = new MainMenu(scanner, createStudentApi, getStudentApi);
            mainMenu.printMenu();
        }
    }
}
