package net.orekhov.taskmanagementsystems.services.implementations;

import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.models.Task;
import net.orekhov.taskmanagementsystems.repositories.TaskRepo;
import net.orekhov.taskmanagementsystems.services.TaskService;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import net.orekhov.taskmanagementsystems.exceptions.TaskNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Этот сервис предоставляет бизнес-логику для работы с задачами, включая создание,
 * получение и удаление задач, а также кэширование и проверку пользователей.
 */

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final PersonServiceImpl personService;

    public TaskServiceImpl(TaskRepo taskRepo, PersonServiceImpl personService) {
        this.taskRepo = taskRepo;
        this.personService = personService;
    }

    /**
     * Возвращает все задачи в виде слайса.
     * @param pageable Параметры пагинации.
     * @return Слайс задач.
     */
    public Slice<Task> findAllSlice(Pageable pageable) {
        return taskRepo.findAllSlice(pageable);
    }

    /**
     * Сохраняет задачу и устанавливает её автора и исполнителя.
     * Использует кэширование для удаления задачи из кэша после её обновления.
     * @param task Задача для сохранения.
     * @throws PersonNotFoundException Если автор или исполнитель не найдены.
     */
    @CacheEvict(value = "tasks", key = "#task.id")
    @Transactional
    public void save(Task task) throws PersonNotFoundException {
        Person author = personService.findByEmail(task.getAuthor().getEmail());
        Person performer = personService.findByEmail(task.getPerformer().getEmail());
        task.setAuthor(author);
        task.setPerformer(performer);
        task.setCreationDate(LocalDateTime.now());
        taskRepo.save(task);
    }

    /**
     * Находит задачу по её ID.
     * Использует кэширование для хранения задачи в кэше.
     * @param id ID задачи.
     * @return Задача.
     * @throws TaskNotFoundException Если задача не найдена.
     */
    @Cacheable(value = "tasks", key = "#id")
    public Task findById(long id) throws TaskNotFoundException {
        return taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    /**
     * Удаляет задачу по её ID.
     * Использует кэширование для удаления задачи из кэша.
     * @param id ID задачи.
     */
    @Transactional
    @CacheEvict(value = "tasks", key = "#id")
    public void delete(long id) {
        taskRepo.deleteById(id);
    }

    /**
     * Находит задачи по ID автора.
     * @param id ID автора.
     * @param pageable Параметры пагинации.
     * @return Слайс задач.
     */
    public Slice<Task> findByAuthorId(long id, Pageable pageable) {
        return taskRepo.findByAuthorId(id, pageable);
    }

    /**
     * Находит задачи по ID исполнителя.
     * @param id ID исполнителя.
     * @param pageable Параметры пагинации.
     * @return Слайс задач.
     */
    public Slice<Task> findByPerformerId(long id, Pageable pageable) {
        return taskRepo.findByPerformerId(id, pageable);
    }
}
