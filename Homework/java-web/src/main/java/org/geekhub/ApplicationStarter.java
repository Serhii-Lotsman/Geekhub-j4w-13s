package org.geekhub;

import org.geekhub.service.CaesarEncrypt;
import org.geekhub.service.InjectableExecutor;

public class ApplicationStarter {

    public static void main(String[] args) {
        InjectableExecutor executor = new InjectableExecutor();
        CaesarEncrypt caesarEncrypt = new CaesarEncrypt();
        executor.getAnnotatedFieldValue(CaesarEncrypt.class);
        System.out.println(caesarEncrypt.cipher("Wow it works!"));
    }
}
