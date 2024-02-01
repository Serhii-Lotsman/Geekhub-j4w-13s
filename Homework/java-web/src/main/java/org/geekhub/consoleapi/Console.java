package org.geekhub.consoleapi;

import org.geekhub.service.HistoryManager;

import java.util.Map;
import java.util.Scanner;

public class Console {

    private final Map<String, String> assortment = Map.of(
        "EXIT", "| [0] - Exit",
        "ENCRYPT", "| [1] - Encrypt message",
        "HISTORY", "| [2] - View history",
        "CAESAR", "[1] - Caesar encrypt",
        "VIGENERE", "[2] - Vigenere encrypt",
        "ALL", "[1] - All history",
        "COUNTER", "[2] - Count of usage of all algorithms",
        "DATE", "[3] - By specific date",
        "UNIQUE", "[4] - Unique encryption"
    );

    private final Scanner scanner;
    private final HistoryManager historyManager;

    public Console(HistoryManager historyManager) {
        this.scanner = new Scanner(System.in);
        this.historyManager = historyManager;
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
                case 2 -> printHistoryMessage();
                default -> System.out.println("Only [0], [1], [2] available");
            }
        } while (option != 0);
    }

    private void encryptMethod() {
        int subOption;
        System.out.println("Choose an encryption method: ");
        System.out.println(assortment.get("CAESAR"));
        System.out.println(assortment.get("VIGENERE"));
        subOption = scanner.nextInt();
        scanner.nextLine();
        switch (subOption) {
           // case 1, 2 -> historyManager.saveMessage(scanner.nextLine());
            default -> System.out.println("Main menu");
        }
    }

    private void printHistoryMessage() {
        int subOption;
        System.out.println("Select the view history option: ");
        System.out.println(assortment.get("ALL"));
        System.out.println(assortment.get("COUNTER"));
        System.out.println(assortment.get("DATE"));
        System.out.println(assortment.get("UNIQUE"));
        subOption = scanner.nextInt();
        scanner.nextLine();
        switch (subOption) {
            //case 1 -> historyManager.getAllHistory();
            //case 2 -> historyManager.getCountOfUsage();
            //case 3 -> historyManager.getMessageByDate(scanner.nextLine());
            //case 4 -> historyManager.getUniqueMessages();
            default -> System.out.println("Main menu");
        }
    }
}
