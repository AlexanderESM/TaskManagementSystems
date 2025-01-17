package net.orekhov.taskmanagementsystems.services.implementations;

import net.orekhov.taskmanagementsystems.models.Comment;
import net.orekhov.taskmanagementsystems.repositories.CommentRepo;
import net.orekhov.taskmanagementsystems.services.CommentService;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Этот сервис инкапсулирует бизнес-логику работы с комментариями и позволяет работать с репозиторием
 * для выполнения операций сохранения, удаления и получения данных о комментариях
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo; // Репозиторий для работы с сущностью Comment

    public CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo; // Внедрение зависимости репозитория
    }

    /**
     * Сохранение комментария в базе данных.
     * @param comment комментарий для сохранения.
     */
    @Transactional
    public void save(Comment comment) {
        comment.setCreationDate(LocalDateTime.now()); // Устанавливаем текущую дату и время как дату создания
        commentRepo.save(comment); // Сохраняем комментарий в базе данных
    }

    /**
     * Удаление комментария по его id.
     * @param id id комментария для удаления.
     */
    @Transactional
    public void delete(long id) {
        commentRepo.deleteById(id); // Удаляем комментарий по id
    }

    /**
     * Получение email автора комментария по id.
     * @param id id комментария.
     * @return email автора комментария.
     * @throws PersonNotFoundException если комментарий с таким id не найден.
     */
    public String findAuthorEmailById(long id) {
        return commentRepo.findAuthorEmailById(id)
                .orElseThrow(() -> new PersonNotFoundException(id)); // Если комментарий не найден, выбрасываем исключение
    }
}
