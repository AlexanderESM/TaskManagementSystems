package net.orekhov.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Класс, представляющий входящий комментарий.
 * Используется для передачи данных о комментарии в системе.
 */
@Getter  // Генерация геттеров
@Setter  // Генерация сеттеров
@Builder  // Шаблон Builder для удобного создания объектов
@NoArgsConstructor  // Генерация конструктора без параметров
@AllArgsConstructor  // Генерация конструктора с параметрами
@Schema(description = "Входящий комментарий")  // Описание модели для Swagger
public class CommentDto {

    /**
     * Текст комментария, не может быть пустым.
     * Обязательное поле для создания нового комментария.
     */
    @Schema(description = "Текст комментария")  // Описание поля для Swagger
    @NotBlank  // Обеспечивает, что текст не может быть пустым
    private String text;

    /**
     * Автор комментария, не может быть пустым.
     * Это поле доступно только для чтения и автоматически заполняется при сохранении комментария.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)  // Указывает, что это поле доступно только для чтения
    @NotBlank  // Обеспечивает, что автор не может быть пустым
    private String author;
}
