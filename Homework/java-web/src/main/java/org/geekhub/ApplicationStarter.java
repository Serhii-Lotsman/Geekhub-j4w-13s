package org.geekhub;

import org.geekhub.consoleapi.Console;
import org.geekhub.model.Message;
import org.geekhub.repository.EncryptedMessageRepositoryImpl;
import org.geekhub.service.HistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ApplicationStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        HistoryManager historyManager = context.getBean(HistoryManager.class);
        Console console = new Console(historyManager);
        console.mainMenu();


        //EncryptedMessageRepositoryImpl messageRepository = context.getBean(EncryptedMessageRepositoryImpl.class);
        /*List<Message> messages = messageRepository.findAll();
        for(Message message : messages) {
            System.out.printf("[%s, %s, %s, %s, %s] %n",message.getUserId(), message.getOriginalMessage(), message.getEncryptedMessage(),
                message.getAlgorithm(), message.getDate());
        }*/
        /*messageRepository.saveMessage(new Message(
                132,
            "OriginalMessage4",
            "EncryptedMessage4",
            "caesar",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            )
        );*/
        /*List<Message> messages = messageRepository.findByAlgorithm("caesar");
        for(Message message : messages) {
            System.out.printf("[%s, %s, %s, %s, %s] %n", message.getUserId(), message.getOriginalMessage(), message.getEncryptedMessage(),
                message.getAlgorithm(), message.getDate());
        }
    }*/
        /*OffsetDateTime dateFrom = OffsetDateTime.of(LocalDateTime.of(2024, Month.FEBRUARY, 2, 20, 19, 40), ZoneOffset.UTC);
        OffsetDateTime dateTo = OffsetDateTime.of(LocalDateTime.of(2024, Month.FEBRUARY, 2, 20, 19, 50), ZoneOffset.UTC);
        List<Message> messages = messageRepository.findByDate(dateFrom, null);
        for (Message message : messages) {
            System.out.printf("[%s, %s, %s, %s, %s] %n", message.getUserId(), message.getOriginalMessage(), message.getEncryptedMessage(),
                message.getAlgorithm(), message.getDate());
        }*/
    }
}
