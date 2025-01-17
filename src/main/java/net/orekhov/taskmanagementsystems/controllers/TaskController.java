package net.orekhov.taskmanagementsystems.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.orekhov.taskmanagementsystems.dto.CommentDto;
import net.orekhov.taskmanagementsystems.dto.PersonDto;
import net.orekhov.taskmanagementsystems.dto.TaskDto;
import net.orekhov.taskmanagementsystems.dto.TaskOutDto;
import net.orekhov.taskmanagementsystems.models.Comment;
import net.orekhov.taskmanagementsystems.models.Task;
import net.orekhov.taskmanagementsystems.services.CommentService;
import net.orekhov.taskmanagementsystems.services.PersonService;
import net.orekhov.taskmanagementsystems.services.TaskService;
import net.orekhov.taskmanagementsystems.services.implementations.CommentServiceImpl;
import net.orekhov.taskmanagementsystems.services.implementations.PersonServiceImpl;
import net.orekhov.taskmanagementsystems.services.implementations.TaskServiceImpl;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import net.orekhov.taskmanagementsystems.exceptions.TaskNotFoundException;
import net.orekhov.taskmanagementsystems.mappers.CommentMapper;
import net.orekhov.taskmanagementsystems.mappers.TaskMapper;
import net.orekhov.taskmanagementsystems.mappers.TaskOutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления задачами (Task).
 * Предоставляет эндпоинты для создания, получения, изменения, удаления задач,
 * а также добавления и удаления комментариев.
 */
@Tag(name = "TaskController", description = "Контроллер предоставляющий эндпоинты для взаимодействие с записями Task")
@RestController
@RequestMapping("/tasks")  // Все запросы в этом контроллере начинаются с "/tasks"
public class TaskController {
    private final TaskService taskService;
    private final PersonService personService;
    private final CommentService commentService;
    private final TaskMapper taskMapper;
    private final TaskOutMapper taskOutputMapper;
    private final CommentMapper commentMapper;

    @Autowired
    public TaskController(TaskServiceImpl taskService, PersonServiceImpl personService, CommentServiceImpl commentService, TaskMapper taskMapper, TaskOutMapper taskOutputMapper, CommentMapper commentMapper) {
        this.taskService = taskService;
        this.personService = personService;
        this.commentService = commentService;
        this.taskMapper = taskMapper;
        this.taskOutputMapper = taskOutputMapper;
        this.commentMapper = commentMapper;
    }

    /**
     * Получение всех задач с пагинацией.
     *
     * @param offset смещение для пагинации
     * @param limit лимит количества задач
     * @return Список задач в формате Slice
     */
    @Operation(
            summary = "Получение всех записей",
            description = "Позволяет получить все записи из БД"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @GetMapping
    public ResponseEntity<Slice<TaskOutDto>> tasks(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return new ResponseEntity<>(taskService
                .findAllSlice(PageRequest.of(offset, limit))
                .map(taskOutputMapper::objToDto), HttpStatus.OK);
    }

    /**
     * Получение задач по id автора.
     *
     * @param id идентификатор автора
     * @param offset смещение для пагинации
     * @param limit лимит количества задач
     * @return Список задач данного автора в формате Slice
     */
    @Operation(
            summary = "Получение всех задач по id автора",
            description = "Позволяет получить все задачи по id автора"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @GetMapping("/author/{id}")
    public ResponseEntity<Slice<TaskOutDto>> tasksByAuthorId(
            @PathVariable("id") long id,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Slice<TaskOutDto> tasks = taskService
                .findByAuthorId(id, PageRequest.of(offset, limit))
                .map(taskOutputMapper::objToDto);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Получение задач по id исполнителя.
     *
     * @param id идентификатор исполнителя
     * @param offset смещение для пагинации
     * @param limit лимит количества задач
     * @return Список задач, назначенных данному исполнителю, в формате Slice
     */
    @Operation(
            summary = "Получение всех задач по id исполнителя",
            description = "Позволяет получить все задачи по id исполнителя"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @GetMapping("/performer/{id}")
    public ResponseEntity<Slice<TaskOutDto>> tasksByPerformerId(
            @PathVariable("id") long id,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Slice<TaskOutDto> tasks = taskService
                .findByPerformerId(id, PageRequest.of(offset, limit))
                .map(taskOutputMapper::objToDto);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Создание новой задачи.
     * Автором задачи является текущий аутентифицированный пользователь.
     *
     * @param taskDto данные для создания задачи
     * @return HTTP статус 201 (создано) в случае успеха
     */
    @Operation(
            summary = "Создание новой задачи",
            description = "Позволяет создать новую задачу"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @PostMapping
    public ResponseEntity<HttpStatus> createTask(@Valid @RequestBody TaskDto taskDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        taskDto.setAuthor(new PersonDto(authentication.getName()));
        Task task = taskMapper.dtoToObj(taskDto);
        try {
            taskService.save(task);
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Получение задачи по ее id.
     *
     * @param id идентификатор задачи
     * @return Задача в формате TaskOutDto
     */
    @Operation(
            summary = "Получение задачи по ее id",
            description = "Позволяет получить задачу по ее id"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @GetMapping("/{id}")
    public ResponseEntity<TaskOutDto> getTask(@PathVariable("id") long id) {
        try {
            Task task = taskService.findById(id);
            TaskOutDto taskOutputDto = taskOutputMapper.objToDto(task);
            return new ResponseEntity<>(taskOutputDto, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удаление задачи по ее id.
     * Доступно только автору задачи.
     *
     * @param id идентификатор задачи
     * @return HTTP статус 204 (нет содержимого) в случае успеха
     */
    @Operation(
            summary = "Удаление задачи по ее id",
            description = "Позволяет удалить задачу по ее id"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {
        try {
            taskService.findById(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (taskService.findById(id).getAuthor().getEmail().equals(authentication.getName())) {
                taskService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Изменение задачи по ее id.
     * Может быть выполнено автором задачи или исполнителем.
     *
     * @param id идентификатор задачи
     * @param taskDto новые данные для задачи
     * @return HTTP статус 200 (ок) в случае успеха
     */
    @Operation(
            summary = "Изменение задачи по ее id",
            description = "Позволяет изменить задачу по ее id"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> changeTask(@PathVariable("id") long id,
                                                 @Valid @RequestBody TaskDto taskDto) {
        try {
            Task task = taskService.findById(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Task changedTask = taskMapper.dtoToObj(taskDto);
            if (task.getAuthor().getEmail().equals(authentication.getName())) {
                taskService.save(changedTask);
                return new ResponseEntity<>(HttpStatus.OK);
            } else if (task.getPerformer().getEmail().equals(authentication.getName())) {
                task.setTaskStatus(changedTask.getTaskStatus());
                taskService.save(task);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Добавление комментария к задаче по id задачи.
     *
     * @param id идентификатор задачи
     * @param commentDto данные комментария
     * @return HTTP статус 201 (создано) в случае успеха
     */
    @Operation(
            summary = "Добавление комментария к задаче по id задачи",
            description = "Позволяет оставить комментарий под задачей"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @PatchMapping("/{id}/comments")
    public ResponseEntity<HttpStatus> addComment(@PathVariable("id") long id,
                                                 @Valid @RequestBody CommentDto commentDto) {
        try {
            Task task = taskService.findById(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Comment comment = commentMapper.dtoToObj(commentDto);
            comment.setAuthor(authentication.getName());
            comment.setTask(task);
            commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Удаление комментария по id комментария.
     * Доступно только автору комментария.
     *
     * @param id идентификатор задачи
     * @param cid идентификатор комментария
     * @return HTTP статус 204 (нет содержимого) в случае успеха
     */
    @Operation(
            summary = "Удаление комментария",
            description = "Позволяет удалить комментарий под задачей"
    )
    @SecurityRequirement(name = "JWT")  // Требуется авторизация через JWT
    @DeleteMapping("/{id}/comments/{cid}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id,
                                                    @PathVariable("cid") long cid) {
        try {
            taskService.findById(id);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        try {
            if (commentService.findAuthorEmailById(cid).equals(authentication.getName())) {
                commentService.delete(cid);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (PersonNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
