package net.orekhov.taskmanagementsystems.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий ответ на запрос аутентификации.
 * Содержит поле token, которое является JWT-токеном для авторизованного пользователя.
 */
@Data  // Генерация геттеров, сеттеров, equals, hashCode и toString
@Builder  // Шаблон Builder для удобного создания объектов
@NoArgsConstructor  // Генерация конструктора без параметров
@AllArgsConstructor  // Генерация конструктора с параметрами
public class AuthenticationResponse {

    /**
     * JWT токен для авторизованного пользователя.
     * Этот токен используется для выполнения аутентифицированных запросов.
     */
    private String token;  // Поле для хранения токена
}
