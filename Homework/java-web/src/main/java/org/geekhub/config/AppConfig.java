package org.geekhub.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("org.geekhub")
@PropertySource("classpath:application.properties")
public class AppConfig {
}

