package net.orekhov.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import net.orekhov.taskmanagementsystems.enums.TaskPriority;
import net.orekhov.taskmanagementsystems.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс, представляющий исходящие данные для задачи.
 * Используется для передачи информации о задаче, которая возвращается в ответе.
 */
@Getter  // Генерация геттеров для всех полей
@Setter  // Генерация сеттеров для всех полей
@Builder  // Шаблон Builder для удобного создания объектов
@NoArgsConstructor  // Генерация конструктора без параметров
@AllArgsConstructor  // Генерация конструктора с параметрами
@Schema(description = "Исходящая задача")  // Описание класса для Swagger
public class TaskOutDto {

    /**
     * Заголовок задачи, который кратко описывает суть задачи.
     */
    @Schema(description = "Заголовок задачи")  // Описание поля для Swagger
    private String header;

    /**
     * Описание задачи, которое предоставляет более детальную информацию о задаче.
     */
    @Schema(description = "Описание задачи")  // Описание поля для Swagger
    private String description;

    /**
     * Статус задачи, который может быть одним из значений:
     * PENDING, IN PROGRESS, COMPLETED.
     */
    @Schema(description = "Статус задачи", example = "PENDING/PROGRESS/COMPLETED")  // Описание поля для Swagger
    private TaskStatus taskStatus;

    /**
     * Приоритет задачи, который может быть LOW, MIDDLE или HIGH.
     */
    @Schema(description = "Приоритет выполнения задачи", example = "LOW/MIDDLE/HIGH")  // Описание поля для Swagger
    private TaskPriority taskPriority;

    /**
     * Автор задачи, который является лицом, создавшим задачу.
     */
    @Schema(description = "Email автора задачи")  // Описание поля для Swagger
    private PersonDto author;

    /**
     * Исполнитель задачи, которому назначена задача для выполнения.
     */
    @Schema(description = "Email исполнителя задачи")  // Описание поля для Swagger
    private PersonDto performer;

    /**
     * Список комментариев, оставленных под задачей.
     */
    @Schema(description = "Комментарии под задачей")  // Описание поля для Swagger
    private List<CommentOutDto> comments;

    /**
     * Дата и время создания задачи.
     */
    @Schema(description = "Дата создания задачи")  // Описание поля для Swagger
    private LocalDateTime creationDate;

}
