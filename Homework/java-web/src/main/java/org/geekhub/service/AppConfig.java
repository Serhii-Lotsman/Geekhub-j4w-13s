package org.geekhub.service;

import org.geekhub.consoleapi.HistoryPrinter;
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
    @Profile("caesarCipher")
    public CaesarCipher caesarCipher() {
        return new CaesarCipher();
    }

    @Bean
    @Profile("vigenereCipher")
    public VigenereCipher vigenereCipher() {
        return new VigenereCipher();
    }

    @Bean
    public HistoryPrinter historyPrinter() {
        return new HistoryPrinter();
    }
}

