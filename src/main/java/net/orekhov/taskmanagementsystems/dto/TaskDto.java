package net.orekhov.taskmanagementsystems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.orekhov.taskmanagementsystems.enums.TaskPriority;
import net.orekhov.taskmanagementsystems.enums.TaskStatus;

/**
 * Класс, представляющий входящие данные для задачи.
 * Используется для передачи данных о задаче при её создании или обновлении.
 */
@Getter  // Генерация геттеров для всех полей
@Setter  // Генерация сеттеров для всех полей
@Builder  // Шаблон Builder для удобного создания объектов
@NoArgsConstructor  // Генерация конструктора без параметров
@AllArgsConstructor  // Генерация конструктора с параметрами
@Schema(description = "Входящая задача")  // Описание класса для Swagger
public class TaskDto {

    /**
     * Заголовок задачи, который кратко описывает суть задачи.
     * Это обязательное поле.
     */
    @Schema(description = "Заголовок задачи")  // Описание поля для Swagger
    @NotBlank  // Поле не может быть пустым
    private String header;

    /**
     * Описание задачи, которое предоставляет более детальную информацию о задаче.
     * Это обязательное поле.
     */
    @Schema(description = "Описание задачи")  // Описание поля для Swagger
    @NotBlank  // Поле не может быть пустым
    private String description;

    /**
     * Статус задачи, который может быть одним из значений:
     * PENDING, IN PROGRESS, COMPLETED.
     * Это обязательное поле.
     */
    @Schema(description = "Статус задачи", example = "PENDING/PROGRESS/COMPLETED")  // Описание поля для Swagger
    @NotNull  // Поле не может быть null
    private TaskStatus taskStatus;

    /**
     * Приоритет задачи, который может быть LOW, MIDDLE или HIGH.
     * Это обязательное поле.
     */
    @Schema(description = "Приоритет выполнения задачи", example = "LOW/MIDDLE/HIGH")  // Описание поля для Swagger
    @NotNull  // Поле не может быть null
    private TaskPriority taskPriority;

    /**
     * Автор задачи, который является лицом, создавшим задачу.
     * Это поле доступно только для чтения.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)  // Поле доступно только для чтения
    private PersonDto author;

    /**
     * Исполнитель задачи, которому назначена задача для выполнения.
     * Это обязательное поле.
     */
    @Schema(description = "Email исполнителя задачи")  // Описание поля для Swagger
    @NotNull  // Поле не может быть null
    private PersonDto performer;

    /**
     * Переопределенный метод toString для отображения данных задачи в строковом формате.
     * @return строковое представление задачи
     */
    @Override
    public String toString() {
        return "TaskDto{" +
                "header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskPriority=" + taskPriority +
                ", author=" + author +
                ", performer=" + performer +
                '}';
    }
}
