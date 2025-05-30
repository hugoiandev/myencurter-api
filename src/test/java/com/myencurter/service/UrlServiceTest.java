package com.myencurter.service;

import com.myencurter.model.Url;
import com.myencurter.model.User;
import com.myencurter.repository.UrlRepository;
import com.myencurter.repository.UserRepository;
import com.myencurter.util.HashGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HashGenerator hashGenerator;

    @InjectMocks
    private UrlService urlService;

    @Captor
    private ArgumentCaptor<Url> urlSaveArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> urlGetArgumentCaptor;

    @Nested
    class SaveUrl {

        @Test
        @DisplayName("Deve criar e retornar id e url.")
        void shouldSaveUrlAndReturnUrl() {

            User user = new User();
            user.setId(1L);
            user.setName("Hugo Ian da Silva");
            user.setEmail("ahugaao@gmail.com");

            Url url = new Url();
            url.setUrl("https://www.google.com");
            url.setId("Hyefj9d");

            when(userRepository.findByEmail("ahugaao@gmail.com")).thenReturn(Optional.of(user));

            when(hashGenerator.generateHash()).thenReturn("Hyefj9d");

            doReturn(url).when(urlRepository).save(urlSaveArgumentCaptor.capture());

            Url result = urlService.saveUrl(url.getUrl(), user.getEmail());

            assertNotNull(result);
            assertEquals(result.getId(), urlSaveArgumentCaptor.getValue().getId());
            assertEquals(result.getUrl(), urlSaveArgumentCaptor.getValue().getUrl());

        }

        @Test
        @DisplayName("Deve lançar uma exceção quando não encontrar um usuário.")
        void shouldThrowExceptionWhenUserNotFound() {

            when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> urlService.saveUrl("", ""));

        }
    }

    @Nested
    class GetUrl {

        @Test
        @DisplayName("Deve retornar uma url por id.")
        void shouldReturnUrlById() {

            Url url = new Url();
            url.setUrl("https://www.google.com");
            url.setId("Hyefj9d");

            when(urlRepository.findById(urlGetArgumentCaptor.capture())).thenReturn(Optional.of(url));

            Url result = urlService.getUrl(url.getId());

            assertNotNull(result);
            assertEquals(result.getId(), urlGetArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando não encontrar a url com o id.")
        void shouldThrowExceptionWhenUrlNotFound() {

            when(urlRepository.findById(any())).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> urlService.getUrl(""));

        }

    }

    @Nested
    class GetAllUrls {

        @Test
        @DisplayName("Deve retornar uma lista de urls do usuário.")
        void shouldReturnAllUrls() {
            Url url1 = new Url();
            url1.setId("Hyefj9d");
            url1.setUrl("https://www.google.com");

            Url url2 = new Url();
            url2.setId("Hywesdd");
            url2.setUrl("https://www.linkedin.com");

            List<Url> urls = Arrays.asList(url1, url2);

            User user = new User();
            user.setId(1L);

            when(userRepository.findByEmail("ahugaao@gmail.com")).thenReturn(Optional.of(user));

            when(urlRepository.findAllByUserId(user.getId())).thenReturn(urls);

            List<Url> result = urlService.getAllUrlsByUser("ahugaao@gmail.com");

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("https://www.google.com", result.get(0).getUrl());
            assertEquals("https://www.linkedin.com", result.get(1).getUrl());

        }
    }
}