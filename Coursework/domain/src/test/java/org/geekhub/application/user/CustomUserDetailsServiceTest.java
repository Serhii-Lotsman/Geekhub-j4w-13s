package org.geekhub.application.user;

import org.geekhub.application.exception.UserException;
import org.geekhub.application.exception.UserExistException;
import org.geekhub.application.exception.ValidationException;
import org.geekhub.application.user.model.UserEntity;
import org.geekhub.application.user.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    private static final int USER_ID = 1;
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        customUserDetailsService = new CustomUserDetailsService(userRepository, userRoleRepository);
    }

    @Test
    void testCreateUser_shouldReturnId_whenValidUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@gmail.com");
        userEntity.setPassword("password");

        when(userRepository.saveUser(userEntity)).thenReturn(USER_ID);
        int userId = customUserDetailsService.createUser(userEntity);

        verify(userRepository, times(1)).saveUser(userEntity);
        assertEquals(USER_ID, userId);
    }

    @Test
    void testCreateUser_shouldThrowValidationException_whenInvalidEmail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("invalid_email");
        userEntity.setPassword("password");

        assertThrows(ValidationException.class, () -> customUserDetailsService.createUser(userEntity));
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void testCreateUser_shouldThrowUserExistException_whenEmailExists() {
        UserEntity existingUser = new UserEntity();
        existingUser.setEmail("existing@gmail.com");
        existingUser.setPassword("password");

        when(userRepository.existsByUserEmail("existing@gmail.com")).thenReturn(true);
        assertThrows(UserExistException.class, () -> customUserDetailsService.createUser(existingUser));
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void testIsEmailExist_shouldReturnTrue_whenEmailExists() {
        when(userRepository.existsByUserEmail("existing@gmail.com")).thenReturn(true);
        assertTrue(customUserDetailsService.isEmailExist("existing@gmail.com"));
    }

    @Test
    void testIsEmailExist_shouldReturnFalse_whenEmailDoesNotExist() {
        when(userRepository.existsByUserEmail("nonExistent@gmail.com")).thenReturn(false);
        assertFalse(customUserDetailsService.isEmailExist("nonExistent@gmail.com"));
    }

    @Test
    void testSetUserRole_shouldSetRole_always() {
        doNothing().when(userRoleRepository).assignRole(anyInt(), anyInt());

        customUserDetailsService.setUserRole(USER_ID, 3);
        verify(userRoleRepository, times(1)).assignRole(USER_ID, 3);
    }

    @Test
    void testGetRoleByName_shouldReturnAssignedRole_whenRoleExists() {
        String roleName = "USER";
        UserRole userRole = new UserRole(USER_ID, roleName);

        when(userRoleRepository.findRole(roleName)).thenReturn(Optional.of(userRole));
        UserRole resultRole = customUserDetailsService.getRoleByName(roleName);
        assertEquals(userRole, resultRole);
    }

    @Test
    void testGetRoleByName_shouldThrowUserException_whenRoleNotFound() {
        String roleName = "NON_EXISTING_ROLE";

        when(userRoleRepository.findRole(roleName)).thenReturn(Optional.empty());
        assertThrows(UserException.class, () -> customUserDetailsService.getRoleByName(roleName));
    }

    @Test
    void testLoadUserByUsername_shouldThrowUsernameNotFoundException_whenUserNotFound() {
        when(userRepository.findUserByEmail("nonExistent@gmail.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
            () -> customUserDetailsService.loadUserByUsername("nonExistent@gmail.com"));
    }
}
