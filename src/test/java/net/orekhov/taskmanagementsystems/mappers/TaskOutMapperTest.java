package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.TaskOutDto;
import net.orekhov.taskmanagementsystems.models.Task;
import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.enums.TaskPriority;
import net.orekhov.taskmanagementsystems.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskOutMapperTest {

    private final TaskOutMapper taskOutMapper = Mappers.getMapper(TaskOutMapper.class);

    @Test
    public void testObjToDto() {
        // Создаем объекты Person для авторов и исполнителей
        Person author = new Person();
        author.setId(1L);
        author.setUsername("John Doe");
        author.setEmail("johndoe@example.com");
        author.setPassword("password123"); // Задание пароля, если нужно
        author.setCreatedTasks(null); // Задаем пустой список созданных задач
        author.setPerformedTasks(null); // Задаем пустой список выполненных задач

        Person performer = new Person();
        performer.setId(2L);
        performer.setUsername("Jane Smith");
        performer.setEmail("janesmith@example.com");
        performer.setPassword("password456"); // Задание пароля, если нужно
        performer.setCreatedTasks(null); // Задаем пустой список созданных задач
        performer.setPerformedTasks(null); // Задаем пустой список выполненных задач

        // Создаем объект Task
        Task task = new Task();
        task.setId(1L);
        task.setHeader("Test Task");
        task.setDescription("Description of the task");
        task.setTaskStatus(TaskStatus.PENDING);
        task.setTaskPriority(TaskPriority.HIGH);
        task.setAuthor(author);
        task.setPerformer(performer);
        task.setComments(Arrays.asList()); // Простой список комментариев
        task.setCreationDate(LocalDateTime.now());

        // Преобразуем объект Task в TaskOutDto
        TaskOutDto taskOutDto = taskOutMapper.objToDto(task);

        // Проверяем, что поля были корректно перенесены
        assertEquals(task.getHeader(), taskOutDto.getHeader());
        assertEquals(task.getDescription(), taskOutDto.getDescription());
        assertEquals(task.getTaskStatus(), taskOutDto.getTaskStatus());
        assertEquals(task.getTaskPriority(), taskOutDto.getTaskPriority());
        assertEquals(task.getAuthor().getEmail(), taskOutDto.getAuthor().getEmail());
        assertEquals(task.getPerformer().getEmail(), taskOutDto.getPerformer().getEmail());
        assertEquals(task.getComments(), taskOutDto.getComments());
        assertEquals(task.getCreationDate(), taskOutDto.getCreationDate());
    }

    @Test
    public void testObjToDtoWithNoComments() {
        // Создаем объект Task без комментариев
        Task task = new Task();
        task.setId(1L);
        task.setHeader("Test Task");
        task.setDescription("Description of the task");
        task.setTaskStatus(TaskStatus.PENDING);
        task.setTaskPriority(TaskPriority.HIGH);

        // Создаем и настраиваем автора
        Person author = new Person();
        author.setId(1L);
        author.setUsername("John Doe");
        author.setEmail("johndoe@example.com");
        author.setPassword("password123");
        author.setCreatedTasks(null); // Нет созданных задач
        author.setPerformedTasks(null); // Нет выполненных задач
        task.setAuthor(author);

        // Создаем и настраиваем исполнителя
        Person performer = new Person();
        performer.setId(2L);
        performer.setUsername("Jane Smith");
        performer.setEmail("janesmith@example.com");
        performer.setPassword("password456");
        performer.setCreatedTasks(null); // Нет созданных задач
        performer.setPerformedTasks(null); // Нет выполненных задач
        task.setPerformer(performer);

        // Устанавливаем комментарии как null
        task.setComments(null);

        // Устанавливаем дату создания
        task.setCreationDate(LocalDateTime.now());

        // Преобразуем объект Task в TaskOutDto
        TaskOutDto taskOutDto = taskOutMapper.objToDto(task);

        // Проверяем, что комментарии пусты
        assertEquals(task.getComments(), taskOutDto.getComments());
    }
}
