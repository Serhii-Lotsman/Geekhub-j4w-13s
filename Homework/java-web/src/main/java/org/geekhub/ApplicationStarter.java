package org.geekhub;

import org.geekhub.consoleapi.Console;
import org.geekhub.service.HistoryManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        HistoryManager historyManager = context.getBean(HistoryManager.class);
        Console console = new Console(historyManager);
        console.mainMenu();
    }
}
