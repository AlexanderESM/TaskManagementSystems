package net.orekhov.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Класс, представляющий пользователя.
 * Используется для передачи данных о пользователе в API.
 */
@Getter  // Генерация геттеров
@Setter  // Генерация сеттеров
@Builder  // Шаблон Builder для удобного создания объектов
@NoArgsConstructor  // Генерация конструктора без параметров
@AllArgsConstructor  // Генерация конструктора с параметрами
@Schema(description = "Пользователь")  // Описание модели для Swagger
public class PersonDto {

    /**
     * Email пользователя.
     * Уникальный идентификатор пользователя, использующийся для аутентификации и связи.
     */
    @Schema(description = "Email пользователя")  // Описание поля для Swagger
    private String email;

    /**
     * Переопределение метода toString.
     * Представление объекта PersonDto в виде строки, отображающее его email.
     *
     * @return строковое представление объекта PersonDto
     */
    @Override
    public String toString() {
        return "PersonDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
