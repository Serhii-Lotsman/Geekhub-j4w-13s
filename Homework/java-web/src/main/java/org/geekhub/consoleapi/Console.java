package org.geekhub.consoleapi;

import org.geekhub.service.CaesarEncrypt;

import java.util.Scanner;

public class Console {

    private static final String EXIT = "[0] - exit";
    private static final String ENCRYPT = "[1] - encrypt message";
    private static final String HISTORY = "[2] - view history";

    private final CaesarEncrypt caesarEncrypt;
    private final Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
        this.caesarEncrypt = new CaesarEncrypt();
    }

    public void mainMenu() {
        int option;
        do {
            System.out.println("Please, choose an option");
            System.out.println(ENCRYPT);
            System.out.println(HISTORY);
            System.out.println(EXIT);
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    encryptMessage();
                    break;
                case 2:
                    System.out.println("history");
                    break;
                default:
                    break;
            }
        } while (option != 0);
        scanner.close();
    }

    private void encryptMessage() {
        String encryptedMessage;
        System.out.println("Enter a message to encrypt: ");
        System.out.println("[0] - Back to menu");
        while (true) {
            if (scanner.hasNext("0")) {
                return;
            } else {
                encryptedMessage = caesarEncrypt.cipher(scanner.nextLine());
                System.out.println(encryptedMessage);
            }
        }
    }
}
