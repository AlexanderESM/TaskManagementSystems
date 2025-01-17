package net.orekhov.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс, представляющий исходящий комментарий.
 * Используется для передачи данных о комментарии в ответах API.
 */
@Getter  // Генерация геттеров
@Setter  // Генерация сеттеров
@Builder  // Шаблон Builder для удобного создания объектов
@NoArgsConstructor  // Генерация конструктора без параметров
@AllArgsConstructor  // Генерация конструктора с параметрами
@Schema(description = "Исходящий комментарий")  // Описание модели для Swagger
public class CommentOutDto {

    /**
     * Идентификатор комментария.
     * Уникальный идентификатор, используемый для обращения к конкретному комментарию.
     */
    @Schema(description = "Идентификатор комментария")  // Описание поля для Swagger
    private long id;

    /**
     * Автор комментария.
     * Имя пользователя, оставившего комментарий.
     */
    @Schema(description = "Автор комментария")  // Описание поля для Swagger
    private String author;

    /**
     * Текст комментария.
     * Содержимое комментария, оставленное пользователем.
     */
    @Schema(description = "Текст комментария")  // Описание поля для Swagger
    private String text;

    /**
     * Дата создания комментария.
     * Время, когда был создан комментарий.
     */
    @Schema(description = "Дата создания")  // Описание поля для Swagger
    private LocalDateTime creationDate;
}
