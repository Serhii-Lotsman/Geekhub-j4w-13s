package org.geekhub;

import org.geekhub.service.AppConfig;
import org.geekhub.service.Cipher;
import org.geekhub.service.VigenereCipher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationStarter {

    public static void main(String[] args) {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            context.registerShutdownHook();
            Cipher cipher = context.getBean(VigenereCipher.class);
            System.out.println(cipher.encrypt("Hello, World!"));
        }
    }
}
