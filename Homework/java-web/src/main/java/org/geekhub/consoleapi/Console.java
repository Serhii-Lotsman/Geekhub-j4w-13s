package org.geekhub.consoleapi;

import org.geekhub.repository.MessagesRepository;
import org.geekhub.repository.MessagesInMemory;
import org.geekhub.service.CaesarEncrypt;

import java.util.Map;
import java.util.Scanner;

public class Console {

    private final Map<String, String> assortment = Map.of(
        "BORDER", "+--------------------------+",
        "EXIT", "| [0] - Exit",
        "ENCRYPT", "| [1] - Encrypt message",
        "HISTORY", "| [2] - View history",
        "CAESAR", "| [1] - Caesar encrypt",
        "BACK", "| [0] - Back to menu",
        "AVAILABLE", "Only [0], [1], [2] available"
    );

    private final Scanner scanner;
    private final CaesarEncrypt caesarEncrypt;
    private final MessagesRepository originalMessages;
    private final MessagesRepository encryptedMessages;

    public Console() {
        this.scanner = new Scanner(System.in);
        this.caesarEncrypt = new CaesarEncrypt();
        this.originalMessages = new MessagesInMemory();
        this.encryptedMessages = new MessagesInMemory();
    }

    public void mainMenu() {
        int option;
        do {
            System.out.println("Please, select an option");
            System.out.println(assortment.get("BORDER"));
            System.out.println(assortment.get("ENCRYPT"));
            System.out.println(assortment.get("HISTORY"));
            System.out.println(assortment.get("EXIT"));
            System.out.println(assortment.get("BORDER"));
            option = scanner.nextInt();
            switch (option) {
                case 0 -> scanner.close();
                case 1 -> encryptMethod();
                case 2 -> System.out.println("history");
                default -> System.out.println(assortment.get("AVAILABLE"));
            }
        } while (option != 0);
    }

    private void encryptMethod() {
        int subOption;
        do {
            System.out.println("Choose an encryption method: ");
            System.out.println(assortment.get("BORDER"));
            System.out.println(assortment.get("CAESAR"));
            System.out.println("| [2] - Other");
            System.out.println(assortment.get("BACK"));
            System.out.println(assortment.get("BORDER"));
            subOption = scanner.nextInt();
            scanner.nextLine(); //to prevent showing sub-menu
            switch (subOption) {
                case 0 -> System.out.println("Main menu");
                case 1 -> setCaesarEncrypt();
                case 2 -> System.out.println("Other");
                default -> System.out.println(assortment.get("AVAILABLE"));
            }
        } while (subOption != 0);
    }

    private void setCaesarEncrypt() {
        System.out.println("Enter a message to encrypt: ");
        String originalMessage = scanner.nextLine();
        String encryptedMessage = caesarEncrypt.cipher(originalMessage);
        originalMessages.addMessage(originalMessage);
        encryptedMessages.addMessage(encryptedMessage);
        System.out.println(encryptedMessages.getMessage(encryptedMessages.size() - 1));
    }
}
