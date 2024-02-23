package org.geekhub.service;

import org.geekhub.exception.UserException;
import org.geekhub.model.Message;
import org.geekhub.repository.EncryptedMessageRepository;
import org.geekhub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CipherServiceTest {
    private static final String ORIGINAL_MESSAGE = "Hello";
    private static final String ALGORITHM = "caesar";
    private static final String OPERATION = "ENCRYPT";
    private static final long USER_ID = 1;

    @Mock
    private EncryptedMessageRepository repository;
    @Mock
    private UserRepository userRepository;

    private CipherService cipherService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        cipherService = new CipherService(USER_ID, 3, "key", repository, userService);
    }

    @Test
    void saveMessage_shouldThrowIllegalStateException_whenUserIdNotExist() {
        cipherService = new CipherService(12345, 3, "key", repository, userService);
        assertThrows(UserException.class, () ->
            cipherService.saveMessage(ORIGINAL_MESSAGE, ALGORITHM, OPERATION));
    }

    @Test
    void saveMessage_shouldSaveMessageAndReturnIt_always() {
        when(userService.isUserExist(USER_ID)).thenReturn(true);
        Message savedMessage = cipherService.saveMessage(ORIGINAL_MESSAGE, ALGORITHM, OPERATION);
        assertNotNull(savedMessage);
        verify(repository, times(1)).saveMessage(savedMessage);
    }

    @Test
    void getMessagesByDateAndAlgorithm_shouldReturnMessagesByAlgorithm_whenValidAlgorithm() {
        cipherService.getMessagesByDateAndAlgorithm(ALGORITHM, null, null);
        verify(repository, times(1)).findByAlgorithm(ALGORITHM.toUpperCase());
    }

    @Test
    void getMessagesByDateAndAlgorithm_shouldReturnAllMessages_whenDateRangeIsNull() {
        cipherService.getMessagesByDateAndAlgorithm("", null, null);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getMessagesByDateAndAlgorithm_shouldReturnMessagesByDateRange_whenDateRangeIsSpecified() {
        String dateFrom = "2024-02-10T12:00";
        String dateTo = "2024-02-15T12:00";
        String offset = "+00:00";
        String dateFromWithOffset = dateFrom + offset;
        String dateToWithOffset = dateTo + offset;

        OffsetDateTime fromDateTime = OffsetDateTime.parse(dateFromWithOffset);
        OffsetDateTime toDateTime = OffsetDateTime.parse(dateToWithOffset);

        cipherService.getMessagesByDateAndAlgorithm(ALGORITHM, dateFrom, dateTo);
        verify(repository, times(1)).findByDate(fromDateTime, toDateTime);
    }
}
