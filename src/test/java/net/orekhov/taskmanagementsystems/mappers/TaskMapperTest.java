package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.PersonDto;
import net.orekhov.taskmanagementsystems.dto.TaskDto;
import net.orekhov.taskmanagementsystems.models.Task;
import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.enums.TaskPriority;
import net.orekhov.taskmanagementsystems.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void testDtoToObj() {
        // Создаем объекты Person с пустыми списками для createdTasks и performedTasks
        Person author = new Person();
        author.setId(1L);
        author.setUsername("John Doe");
        author.setEmail("johndoe@example.com");
        author.setPassword("password123");
        author.setRole(null); // Роль можно оставить как null, если она не требуется
        author.setCreatedTasks(null);
        author.setPerformedTasks(null);

        Person performer = new Person();
        performer.setId(2L);
        performer.setUsername("Jane Smith");
        performer.setEmail("janesmith@example.com");
        performer.setPassword("password456");
        performer.setRole(null); // Роль можно оставить как null
        performer.setCreatedTasks(null);
        performer.setPerformedTasks(null);

        // Создаем объект TaskDto
        TaskDto taskDto = TaskDto.builder()
                .header("Test Task")
                .description("Description of the task")
                .taskStatus(TaskStatus.PENDING)
                .taskPriority(TaskPriority.HIGH)
                .author(new PersonDto("johndoe@example.com")) // Используем только email для PersonDto
                .performer(new PersonDto("janesmith@example.com")) // Используем только email для PersonDto
                .build();

        // Преобразуем в объект Task
        Task task = taskMapper.dtoToObj(taskDto);

        // Проверяем, что поля были корректно перенесены
        assertEquals(taskDto.getHeader(), task.getHeader());
        assertEquals(taskDto.getDescription(), task.getDescription());
        assertEquals(taskDto.getTaskStatus(), task.getTaskStatus());
        assertEquals(taskDto.getTaskPriority(), task.getTaskPriority());

        // Проверка, что автор и исполнитель были корректно перенесены
        assertEquals(taskDto.getAuthor().getEmail(), task.getAuthor().getEmail());
        assertEquals(taskDto.getPerformer().getEmail(), task.getPerformer().getEmail());
    }
}
