package org.geekhub;

import org.geekhub.service.AppConfig;
import org.geekhub.service.CaesarCipher;
import org.geekhub.service.VigenereCipher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        context.getEnvironment().setActiveProfiles("caesar", "vigenere");
        context.register(AppConfig.class);

        System.out.println(context.getBean(CaesarCipher.class).encrypt("Message to encrypt"));
        System.out.println(context.getBean(VigenereCipher.class).encrypt("Message to encrypt"));
    }
}
