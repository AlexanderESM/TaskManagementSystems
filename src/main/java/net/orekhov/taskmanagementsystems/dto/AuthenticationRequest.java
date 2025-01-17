package net.orekhov.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий запрос на аутентификацию пользователя.
 * Содержит поля email и пароль для выполнения аутентификации.
 */
@Data  // Генерация геттеров, сеттеров, equals, hashCode и toString
@Builder  // Шаблон Builder для удобного создания объектов
@NoArgsConstructor  // Генерация конструктора без параметров
@AllArgsConstructor  // Генерация конструктора с параметрами
@Schema(description = "Данные пользователя")  // Описание класса для OpenAPI документации
public class AuthenticationRequest {

    @Schema(description = "Email")  // Описание поля для OpenAPI
    @NotEmpty  // Проверка на пустое значение
    private String email;  // Поле для хранения email пользователя

    @Schema(description = "Пароль")  // Описание поля для OpenAPI
    @NotEmpty  // Проверка на пустое значение
    private String password;  // Поле для хранения пароля пользователя
}
