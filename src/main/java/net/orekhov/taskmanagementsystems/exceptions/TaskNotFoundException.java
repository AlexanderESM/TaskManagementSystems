package net.orekhov.taskmanagementsystems.exceptions;

/**
 * Исключение, выбрасываемое, когда задача с указанным id не найдена в системе.
 * Это исключение сигнализирует о том, что попытка найти задачу по id не удалась.
 */
public class TaskNotFoundException extends RuntimeException {

    /**
     * Конструктор исключения.
     * Он вызывает конструктор родительского класса с сообщением о том, что задача с указанным id не найдена.
     */
    public TaskNotFoundException() {
        super("Task with this id not found!");
    }
}
