package net.orekhov.taskmanagementsystems.controllers;

import net.orekhov.taskmanagementsystems.dto.AuthenticationRequest;
import net.orekhov.taskmanagementsystems.dto.AuthenticationResponse;
import net.orekhov.taskmanagementsystems.dto.RegisterRequest;
import net.orekhov.taskmanagementsystems.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тестовый класс для контроллера AuthenticationController.
 * Использует MockMvc для тестирования HTTP-запросов и их обработки.
 */
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc для тестирования HTTP-запросов

    @MockBean
    private AuthenticationService authenticationService; // Мок сервиса аутентификации

    /**
     * Тест успешной регистрации пользователя.
     * Проверяет, что метод register возвращает статус 201 и правильный JSON-ответ.
     */
    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        // Мок ответа сервиса
        AuthenticationResponse mockResponse = new AuthenticationResponse("mock-token");
        Mockito.when(authenticationService.register(any(RegisterRequest.class))).thenReturn(mockResponse);

        // Выполняем POST запрос на регистрацию
        mockMvc.perform(post("/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "newUser",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isCreated()) // Проверяем статус 201
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Проверяем тип контента
                .andExpect(jsonPath("$.token").value("mock-token")); // Проверяем токен в ответе
    }

    /**
     * Тест ошибки регистрации при передаче некорректных данных.
     * Проверяет, что метод register возвращает статус 400.
     */
    @Test
    void shouldReturnBadRequestForInvalidRegistration() throws Exception {
        mockMvc.perform(post("/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "",
                                    "password": ""
                                }
                                """))
                .andExpect(status().isBadRequest()); // Ожидаем статус 400
    }

    /**
     * Тест успешной авторизации пользователя.
     * Проверяет, что метод authenticate возвращает статус 200 и правильный JSON-ответ.
     */
    @Test
    void shouldAuthenticateUserSuccessfully() throws Exception {
        // Мок ответа сервиса
        AuthenticationResponse mockResponse = new AuthenticationResponse("auth-token");
        Mockito.when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(mockResponse);

        // Выполняем POST запрос на авторизацию
        mockMvc.perform(post("/auth/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "testUser",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isOk()) // Проверяем статус 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Проверяем тип контента
                .andExpect(jsonPath("$.token").value("auth-token")); // Проверяем токен в ответе
    }

    /**
     * Тест ошибки авторизации при передаче некорректных данных.
     * Проверяет, что метод authenticate возвращает статус 400.
     */
    @Test
    void shouldReturnBadRequestForInvalidAuthentication() throws Exception {
        mockMvc.perform(post("/auth/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "",
                                    "password": ""
                                }
                                """))
                .andExpect(status().isBadRequest()); // Ожидаем статус 400
    }

    /**
     * Тест ошибки авторизации при неверных учетных данных.
     * Проверяет, что метод authenticate возвращает статус 401.
     */
    @Test
    void shouldReturnUnauthorizedForInvalidCredentials() throws Exception {
        Mockito.when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials")); // Симулируем ошибку сервиса

        mockMvc.perform(post("/auth/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "wrongUser",
                                    "password": "wrongPassword"
                                }
                                """))
                .andExpect(status().isUnauthorized()); // Ожидаем статус 401
    }
}
