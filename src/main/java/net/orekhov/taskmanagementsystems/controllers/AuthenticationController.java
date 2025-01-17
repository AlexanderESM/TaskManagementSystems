package net.orekhov.taskmanagementsystems.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.orekhov.taskmanagementsystems.dto.AuthenticationRequest;
import net.orekhov.taskmanagementsystems.dto.AuthenticationResponse;
import net.orekhov.taskmanagementsystems.dto.RegisterRequest;
import net.orekhov.taskmanagementsystems.services.AuthenticationService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Контроллер для обработки запросов регистрации и авторизации пользователей.
 * Включает два основных метода для регистрации нового пользователя и авторизации уже существующего.
 */
@Tag(name = "AuthenticationController", description = "Контроллер регистрации/авторизации")
@RestController
@RequestMapping("/auth")  // Все пути в контроллере будут начинаться с "/auth"
@RequiredArgsConstructor  // Генерирует конструктор с необходимыми зависимостями
public class AuthenticationController {

    // Сервис для работы с логикой аутентификации и регистрации
    private final AuthenticationService service;

    /**
     * Регистрация нового пользователя.
     * Принимает данные пользователя, выполняет регистрацию и возвращает ответ с информацией.
     *
     * @param request данные для регистрации
     * @return ResponseEntity с данными для ответа
     */
    @Operation(
            summary = "Регистрация пользователя",  // Краткое описание операции для документации OpenAPI
            description = "Позволяет зарегистрировать пользователя"  // Подробное описание операции
    )
    @PostMapping(value = "/registration")  // HTTP POST запрос по адресу "/auth/registration"
    public ResponseEntity<AuthenticationResponse> register(
            @Valid  // Проверка данных в запросе на соответствие правилам в классе RegisterRequest
            @RequestBody RegisterRequest request) {  // Принимает тело запроса в формате JSON
        AuthenticationResponse response = service.register(request);  // Выполнение регистрации через сервис
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.newInstance();  // Строим URI для ответа
        // Возвращаем успешный ответ с кодом 201 (создано), в котором содержится URI для нового пользователя
        return ResponseEntity.created(componentsBuilder
                        .path("/{username}")  // Шаблон пути с параметром username
                        .build(Map.of("username", request.getUsername())))  // Формируем URI с параметром username
                .contentType(MediaType.APPLICATION_JSON)  // Устанавливаем тип контента в JSON
                .body(response);  // Возвращаем тело ответа с данными
    }

    /**
     * Авторизация пользователя.
     * Принимает данные для авторизации, выполняет аутентификацию и возвращает ответ с токеном.
     *
     * @param request данные для авторизации
     * @return ResponseEntity с данными для ответа
     */
    @Operation(
            summary = "Авторизация пользователя",  // Краткое описание операции для документации OpenAPI
            description = "Позволяет авторизовать пользователя"  // Подробное описание операции
    )
    @PostMapping(value = "/authentication")  // HTTP POST запрос по адресу "/auth/authentication"
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid  // Проверка данных в запросе на соответствие правилам в классе AuthenticationRequest
            @RequestBody AuthenticationRequest request  // Принимает тело запроса в формате JSON
    ) {
        AuthenticationResponse response = service.authenticate(request);  // Выполнение аутентификации через сервис
        // Возвращаем успешный ответ с кодом 200 (OK) и токеном в теле ответа
        return ResponseEntity.ok()  // Код ответа 200 (OK)
                .contentType(MediaType.APPLICATION_JSON)  // Устанавливаем тип контента в JSON
                .body(response);  // Возвращаем тело ответа с токеном
    }
}
