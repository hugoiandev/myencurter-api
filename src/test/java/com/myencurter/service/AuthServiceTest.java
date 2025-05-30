package com.myencurter.service;

import com.myencurter.model.User;
import com.myencurter.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Nested
    class SaveUser {

        @Test
        @DisplayName("Deve criar um usuário.")
        void shouldSaveUser() {

            User user = new User();

            authService.saveUser(user);

            verify(userRepository, times(1)).save(user);
        }

    }

    @Nested
    class GetUser {

        @Test
        @DisplayName("Deve retornar um usuário por email.")
        void shouldGetUserByEmail() {

            User user = new User();
            user.setEmail("email@email");
            user.setId(1L);
            user.setName("Hugo");
            user.setPassword("12345678");

            doReturn(Optional.of(user)).when(userRepository).findByEmail("email@email");

            Optional<User> result = authService.getUser("email@email");

            assertNotNull(result);
            assertEquals(user.getEmail(), result.get().getEmail());

        }
    }

}