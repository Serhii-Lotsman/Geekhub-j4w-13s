package org.geekhub;

import org.geekhub.consoleapi.HistoryPrinter;
import org.geekhub.service.AppConfig;
import org.geekhub.service.Cipher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationStarter {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        context.register(AppConfig.class);
        String[] profiles = context.getEnvironment().getActiveProfiles();
        Cipher cipher = context.getBean(profiles[0], Cipher.class);
        HistoryPrinter historyPrinter = context.getBean(HistoryPrinter.class);
        historyPrinter.print(cipher, profiles[0]);
    }
}
