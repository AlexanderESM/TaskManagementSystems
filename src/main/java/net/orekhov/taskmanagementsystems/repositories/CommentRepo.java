package net.orekhov.taskmanagementsystems.repositories;

import net.orekhov.taskmanagementsystems.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Comment (Комментарий).
 * Содержит методы для выполнения операций с базой данных, связанных с комментариями.
 */
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    /**
     * Находит email автора комментария по его идентификатору.
     *
     * @param id Идентификатор комментария.
     * @return Опционально возвращает email автора комментария, если он существует.
     */
    Optional<String> findAuthorEmailById(long id);
}
