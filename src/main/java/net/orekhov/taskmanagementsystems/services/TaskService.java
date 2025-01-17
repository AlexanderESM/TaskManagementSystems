package net.orekhov.taskmanagementsystems.services;

import net.orekhov.taskmanagementsystems.models.Task;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import net.orekhov.taskmanagementsystems.exceptions.TaskNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Этот интерфейс представляет слой сервисов для работы с задачами и их метаданными
 */
public interface TaskService {

    /**
     * Получить все задачи с пагинацией.
     * @param pageable Параметры пагинации.
     * @return Срез задач.
     */
    Slice<Task> findAllSlice(Pageable pageable);

    /**
     * Сохранить новую задачу.
     * @param task Задача для сохранения.
     * @throws PersonNotFoundException если автор или исполнитель не найден.
     */
    void save(Task task) throws PersonNotFoundException;

    /**
     * Найти задачу по ID.
     * @param id ID задачи.
     * @return Задача с указанным ID.
     * @throws TaskNotFoundException если задача не найдена.
     */
    Task findById(long id) throws TaskNotFoundException;

    /**
     * Удалить задачу по ID.
     * @param id ID задачи.
     */
    void delete(long id);

    /**
     * Найти задачи по ID автора.
     * @param id ID автора.
     * @param pageable Параметры пагинации.
     * @return Срез задач, созданных автором.
     */
    Slice<Task> findByAuthorId(long id, Pageable pageable);

    /**
     * Найти задачи по ID исполнителя.
     * @param id ID исполнителя.
     * @param pageable Параметры пагинации.
     * @return Срез задач, выполняемых исполнителем.
     */
    Slice<Task> findByPerformerId(long id, Pageable pageable);
}
