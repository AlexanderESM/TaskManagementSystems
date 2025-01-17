package net.orekhov.taskmanagementsystems.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Конфигурация для OpenAPI.
 * Этот класс используется для настройки документации API с помощью OpenAPI и Swagger.
 * Описание API, контактная информация и настройка безопасности с использованием JWT токенов.
 */
@OpenAPIDefinition(
        // Настройка информации о API
        info = @Info(
                title = "Task System Api",  // Название API
                description = "Task System",  // Описание API
                version = "1.0.0",  // Версия API
                // Контактная информация разработчика API
                contact = @Contact(
                        name = "myName",  // Имя разработчика
                        email = "myMail",  // Email разработчика
                        url = "https://github.com/"  // Ссылка на профиль разработчика
                )
        )
)
@SecurityScheme(
        // Настройка схемы безопасности для API
        name = "JWT",  // Название схемы безопасности
        type = SecuritySchemeType.HTTP,  // Тип схемы безопасности (HTTP)
        bearerFormat = "JWT",  // Формат токена безопасности (JWT)
        scheme = "bearer"  // Тип схемы для использования (bearer)
)
public class OpenApiConfig {
    // Этот класс используется для настройки OpenAPI и Swagger,
    // без необходимости реализации какого-либо функционала.
}
