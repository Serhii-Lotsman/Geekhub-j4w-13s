package org.geekhub;

import org.geekhub.model.Message;
import org.geekhub.repository.EncryptedMessageRepositoryImpl;
import org.geekhub.service.HistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ApplicationStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        String[] profiles = context.getEnvironment().getActiveProfiles();

//        HistoryManager historyManager = context.getBean(HistoryManager.class);
//        historyManager.print(profiles[0]);
        EncryptedMessageRepositoryImpl messageRepository = context.getBean(EncryptedMessageRepositoryImpl.class);
        /*List<Message> messages = messageRepository.findAll();
        for(Message message : messages) {
            System.out.printf("[%s, %s, %s, %s, %s] %n",message.getUserId(), message.getOriginalMessage(), message.getEncryptedMessage(),
                message.getAlgorithm(), message.getDate());
        }*/
        messageRepository.saveMessage(new Message(
                132,
            "OriginalMessage",
            "EncryptedMessage",
            "Algorithm",
            "2024-02-02 13:45:12"
            )
        );
    }
}
