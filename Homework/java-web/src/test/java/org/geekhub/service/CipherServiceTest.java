package org.geekhub.service;

import org.geekhub.model.Message;
import org.geekhub.repository.EncryptedMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class CipherServiceTest {

    @Mock
    private EncryptedMessageRepository repository;

    private CipherService cipherService;

    @BeforeEach
    void setUp() {
        cipherService = new CipherService(12345, 3, "key", repository);
    }

    @Test
    void saveMessage_shouldSaveMessageAndReturnIt_always() {
        String originalMessage = "Hello";
        String algorithm = "caesar";
        String operation = "ENCRYPT";
        Message savedMessage = cipherService.saveMessage(originalMessage, algorithm, operation);
        assertNotNull(savedMessage);
        verify(repository, times(1)).saveMessage(savedMessage);
    }

    @Test
    void getMessagesByDateAndAlgorithm_shouldReturnMessagesByAlgorithm_whenValidAlgorithm() {
        String algorithm = "caesar";
        cipherService.getMessagesByDateAndAlgorithm(algorithm, null, null);
        verify(repository, times(1)).findByAlgorithm(algorithm.toUpperCase());
    }

    @Test
    void getMessagesByDateAndAlgorithm_shouldReturnAllMessages_whenDateRangeIsNull() {
        cipherService.getMessagesByDateAndAlgorithm("", null, null);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getMessagesByDateAndAlgorithm_shouldReturnMessagesByDateRange_whenDateRangeIsSpecified() {
        String algorithm = "caesar";
        String dateFrom = "2024-02-10T12:00";
        String dateTo = "2024-02-15T12:00";
        String offset = "+00:00";
        String dateFromWithOffset = dateFrom + offset;
        String dateToWithOffset = dateTo + offset;

        OffsetDateTime fromDateTime = OffsetDateTime.parse(dateFromWithOffset);
        OffsetDateTime toDateTime = OffsetDateTime.parse(dateToWithOffset);

        cipherService.getMessagesByDateAndAlgorithm(algorithm, dateFrom, dateTo);
        verify(repository, times(1)).findByDate(fromDateTime, toDateTime);
    }
}
