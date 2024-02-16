package org.geekhub.consoleapi;

import org.geekhub.model.Algorithm;
import org.geekhub.model.CipherOperation;
import org.geekhub.service.CipherService;

import java.util.Map;
import java.util.Scanner;

public class Console {

    private final Map<String, String> assortment = Map.of(
        "EXIT", "| [0] - Exit",
        "ENCRYPT", "| [1] - Encrypt/Decrypt message",
        "HISTORY", "| [2] - View history",
        "CAESAR", "[1] - Caesar cipher",
        "VIGENERE", "[2] - Vigenere cipher",
        "ALL", "[1] - All history",
        "COUNTER", "[2] - Count of usage of all algorithms",
        "DATE", "[3] - By specific date",
        "UNIQUE", "[4] - Unique encryption",
        "ALGORITHM", "[5] - By algorithm"
    );

    private final Scanner scanner;
    private final CipherService cipherService;

    public Console(CipherService cipherService) {
        this.scanner = new Scanner(System.in);
        this.cipherService = cipherService;
    }

    public void mainMenu() {
        int option;
        do {
            System.out.println("Please, select an option");
            System.out.println("+--------------------------+");
            System.out.println(assortment.get("ENCRYPT"));
            System.out.println(assortment.get("HISTORY"));
            System.out.println(assortment.get("EXIT"));
            System.out.println("+--------------------------+");
            option = scanner.nextInt();
            switch (option) {
                case 0 -> scanner.close();
                case 1 -> operation();
                case 2 -> printHistoryMessage();
                default -> System.out.println("Only [0], [1], [2] available");
            }
        } while (option != 0);
    }

    private void operation() {
        int subOption;
        System.out.println("What would you like to do: ");
        System.out.println("[1] - Encrypt");
        System.out.println("[2] - Decrypt");
        subOption = scanner.nextInt();
        scanner.nextLine();
        switch (subOption) {
            case 1 -> encryptMethod(CipherOperation.ENCRYPT);
            case 2 -> encryptMethod(CipherOperation.DECRYPT);
            default -> System.out.println("Main menu");
        }
    }

    private void encryptMethod(CipherOperation operation) {
        int subOption;
        System.out.println("Choose method: ");
        System.out.println(assortment.get("CAESAR"));
        System.out.println(assortment.get("VIGENERE"));
        subOption = scanner.nextInt();
        System.out.println("Enter message:");
        scanner.nextLine();
        switch (subOption) {
            case 1 -> cipherService.saveMessage(scanner.nextLine(), Algorithm.CAESAR, operation);
            case 2 -> cipherService.saveMessage(scanner.nextLine(), Algorithm.VIGENERE, operation);
            default -> System.out.println("Main menu");
        }
    }

    private void printHistoryMessage() {
        int subOption;
        subMenu();
        subOption = scanner.nextInt();
        scanner.nextLine();
        switch (subOption) {
            case 1 -> cipherService.getAllHistory();
            case 2 -> cipherService.getCountOfUsage();
            case 3 -> {
                System.out.println("Enter the date and time in the following pattern 'dd-MM-yyyy HH:mm:ss'");
                cipherService.getMessageByDate(setDate("from"), setDate("to"));
            }
            case 4 -> cipherService.getUniqueMessages();
            case 5 -> setAlgorithm();
            case 6 -> cipherService.getFailedMessage();
            default -> System.out.println("Main menu");
        }
    }

    private void subMenu() {
        System.out.println("Select the view history option: ");
        System.out.println(assortment.get("ALL"));
        System.out.println(assortment.get("COUNTER"));
        System.out.println(assortment.get("DATE"));
        System.out.println(assortment.get("UNIQUE"));
        System.out.println(assortment.get("ALGORITHM"));
        System.out.println("[6] - By status 'failed'");
    }

    private String setDate(String range) {
        System.out.printf("Enter the date-range %s: ", range);
        return scanner.nextLine();
    }

    private void setAlgorithm() {
        int subOption;
        System.out.println("Select available algorithm:");
        System.out.println(assortment.get("CAESAR"));
        System.out.println(assortment.get("VIGENERE"));
        subOption = scanner.nextInt();
        scanner.nextLine();
        switch (subOption) {
            case 1 -> cipherService.getMessageByAlgorithm(Algorithm.CAESAR.name());
            case 2 -> cipherService.getMessageByAlgorithm(Algorithm.VIGENERE.name());
            default -> System.out.println("Main menu");
        }
    }
}
