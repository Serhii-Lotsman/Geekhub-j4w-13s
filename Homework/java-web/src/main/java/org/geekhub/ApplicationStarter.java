package org.geekhub;

import org.geekhub.service.CaesarEncrypt;

public class ApplicationStarter {

    public static void main(String[] args) {
        CaesarEncrypt caesarEncrypt = new CaesarEncrypt();
        System.out.println(caesarEncrypt.cipher("Wow it works!", 3));
    }
}
