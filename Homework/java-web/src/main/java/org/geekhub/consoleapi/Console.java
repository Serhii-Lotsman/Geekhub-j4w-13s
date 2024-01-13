package org.geekhub.consoleapi;

import org.geekhub.service.CaesarCipher;
import org.geekhub.service.CipherManager;
import org.geekhub.service.SubstitutionCipher;

import java.util.Map;
import java.util.Scanner;

public class Console {

    private final Map<String, String> assortment = Map.of(
        "EXIT", "| [0] - Exit",
        "ENCRYPT", "| [1] - Encrypt message",
        "HISTORY", "| [2] - View history",
        "CAESAR", "[1] - Caesar encrypt",
        "SUBSTITUTION", "[2] - Substitution encrypt"
    );

    private final Scanner scanner;
    private final CipherManager cipherManager;

    public Console() {
        this.scanner = new Scanner(System.in);
        this.cipherManager = new CipherManager();
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
                case 2 -> cipherManager.getLoadHistory();
                default -> System.out.println("Only [0], [1], [2] available");
            }
        } while (option != 0);
    }

    private void encryptMethod() {
        int subOption;
        System.out.println("Choose an encryption method: ");
        System.out.println(assortment.get("CAESAR"));
        System.out.println(assortment.get("SUBSTITUTION"));
        subOption = scanner.nextInt();
        scanner.nextLine(); //to prevent showing sub-menu
        switch (subOption) {
            case 1 -> cipherManager.setEncrypt(scanner, CaesarCipher.class);
            case 2 -> cipherManager.setEncrypt(scanner, SubstitutionCipher.class);
            default -> System.out.println("Only [0], [1], [2] available");
        }
    }
}
