package org.geekhub;

import org.geekhub.config.AppConfig;
import org.geekhub.consoleapi.Console;
import org.geekhub.service.HistoryManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        HistoryManager historyManager = context.getBean(HistoryManager.class);
        runApp(historyManager);
    }

    private static void runApp(HistoryManager historyManager) {
        Console console = new Console(historyManager);
        console.mainMenu();
    }
}
