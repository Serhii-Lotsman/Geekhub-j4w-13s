package org.geekhub;

import org.geekhub.service.HistoryManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationStarter {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        String[] profiles = context.getEnvironment().getActiveProfiles();

        HistoryManager historyManager = context.getBean(HistoryManager.class);
        historyManager.print(profiles[0]);
    }
}
