package net.orekhov.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий запрос на регистрацию пользователя.
 * Используется для передачи данных о новом пользователе при регистрации.
 */
@Data  // Генерация стандартных методов (toString, equals, hashCode и геттеры/сеттеры)
@Builder  // Шаблон Builder для удобного создания объектов
@NoArgsConstructor  // Генерация конструктора без параметров
@AllArgsConstructor  // Генерация конструктора с параметрами
public class RegisterRequest {

    /**
     * Имя пользователя, которое будет использоваться для аутентификации.
     * Это обязательное поле.
     */
    @Schema(description = "Имя пользователя")  // Описание поля для Swagger
    @NotEmpty  // Поле не может быть пустым
    private String username;

    /**
     * Email пользователя, который будет использоваться для связи и аутентификации.
     * Это обязательное поле.
     */
    @Schema(description = "Email")  // Описание поля для Swagger
    @NotEmpty  // Поле не может быть пустым
    private String email;

    /**
     * Пароль пользователя, который будет использоваться для аутентификации.
     * Это обязательное поле.
     */
    @Schema(description = "Пароль")  // Описание поля для Swagger
    @NotEmpty  // Поле не может быть пустым
    private String password;
}
