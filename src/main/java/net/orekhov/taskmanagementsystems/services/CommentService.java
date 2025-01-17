package net.orekhov.taskmanagementsystems.services;

import net.orekhov.taskmanagementsystems.models.Comment;

/**
 * интерфейс CommentService задает контракт для сервисов работы с комментариями
 */
public interface CommentService {

    /**
     * Сохранение комментария.
     * @param comment Комментарий для сохранения.
     */
    void save(Comment comment);

    /**
     * Удаление комментария по его идентификатору.
     * @param id Идентификатор комментария.
     */
    void delete(long id);

    /**
     * Получение email автора комментария по его идентификатору.
     * @param id Идентификатор комментария.
     * @return Email автора комментария.
     */
    String findAuthorEmailById(long id);
}
