package org.geekhub;

import org.geekhub.service.AppConfig;
import org.geekhub.service.Cipher;
import org.geekhub.service.CipherManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationStarter {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        context.register(AppConfig.class);
        String[] profiles = context.getEnvironment().getActiveProfiles();
        Cipher cipher = context.getBean(profiles[0], Cipher.class);
        CipherManager cipherManager = new CipherManager(cipher);
        System.out.println(cipherManager.getEncryptedMessage("Message to encrypt"));
    }
}
