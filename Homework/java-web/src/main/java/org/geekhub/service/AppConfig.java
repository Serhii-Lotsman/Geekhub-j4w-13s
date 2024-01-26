package org.geekhub.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    @Profile("caesar")
    public CaesarCipher caesarCipher() {
        return new CaesarCipher();
    }

    @Bean
    @Profile("vigenere")
    public VigenereCipher vigenereCipher() {
        return new VigenereCipher();
    }
}

