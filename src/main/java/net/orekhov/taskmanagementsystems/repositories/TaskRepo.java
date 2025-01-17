package net.orekhov.taskmanagementsystems.repositories;

import net.orekhov.taskmanagementsystems.models.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью Task (Задача).
 * Содержит методы для выполнения операций с базой данных, связанных с задачами.
 */
@Repository
public interface TaskRepo extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    /**
     * Находит задачи, созданные пользователем по его id, с поддержкой пагинации.
     *
     * @param id       Идентификатор пользователя (автора).
     * @param pageable Параметры пагинации.
     * @return Частичная выборка задач (Slice).
     */
    Slice<Task> findByAuthorId(long id, Pageable pageable);

    /**
     * Находит задачи, выполняемые пользователем по его id, с поддержкой пагинации.
     *
     * @param id       Идентификатор пользователя (исполнителя).
     * @param pageable Параметры пагинации.
     * @return Частичная выборка задач (Slice).
     */
    Slice<Task> findByPerformerId(long id, Pageable pageable);

    /**
     * Возвращает все задачи с поддержкой пагинации.
     *
     * @param pageable Параметры пагинации.
     * @return Частичная выборка задач (Slice).
     */
    @Query(
            value = "select * from entities.task",
            nativeQuery = true)
    Slice<Task> findAllSlice(Pageable pageable);
}
