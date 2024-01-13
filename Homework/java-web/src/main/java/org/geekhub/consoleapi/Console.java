package org.geekhub.consoleapi;

import org.geekhub.repository.LogInMemory;
import org.geekhub.repository.LogRepository;
import org.geekhub.service.CaesarEncrypt;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class Console {

    private final Map<String, String> assortment = Map.of(
        "EXIT", "| [0] - Exit",
        "ENCRYPT", "| [1] - Encrypt message",
        "HISTORY", "| [2] - View history",
        "CAESAR", "[1] - Caesar encrypt"
    );

    private final Scanner scanner;
    private final CaesarEncrypt caesarEncrypt;
    private final LogRepository logHistory;

    public Console() {
        this.scanner = new Scanner(System.in);
        this.caesarEncrypt = new CaesarEncrypt();
        this.logHistory = new LogInMemory();
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
                case 1 -> encryptMethod();
                case 2 -> logHistory.loadHistory();
                default -> System.out.println("Only [0], [1], [2] available");
            }
        } while (option != 0);
    }

    private void encryptMethod() {
        int subOption;
        System.out.println("Choose an encryption method: ");
        System.out.println(assortment.get("CAESAR"));
        System.out.println("[2] - Other");
        subOption = scanner.nextInt();
        scanner.nextLine(); //to prevent showing sub-menu
        switch (subOption) {
            case 1 -> setCaesarEncrypt();
            case 2 -> System.out.println("Other");
            default -> System.out.println("Only [0], [1], [2] available");
        }
    }

    private void setCaesarEncrypt() {
        System.out.println("Enter a message to encrypt: ");
        String originalMessage = scanner.nextLine();
        String encryptedMessage = caesarEncrypt.cipher(originalMessage);
        saveLogs(originalMessage, encryptedMessage, new Date());
    }

    private void saveLogs(String originalMessage, String encryptedMessage, Date date) {
        String message = date.toString() + String.format(
            " - Message '%s' was encrypted via %s into '%s'",
            originalMessage, CaesarEncrypt.class.getSimpleName(), encryptedMessage);
        logHistory.addMessage(message);
        System.out.println(logHistory.saveHistory());
    }
}
