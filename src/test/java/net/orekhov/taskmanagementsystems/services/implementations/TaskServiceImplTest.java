package net.orekhov.taskmanagementsystems.services.implementations;

import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.models.Task;
import net.orekhov.taskmanagementsystems.repositories.TaskRepo;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import net.orekhov.taskmanagementsystems.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private PersonServiceImpl personService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllSlice() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Slice<Task> expectedSlice = new PageImpl<>(Collections.emptyList());
        when(taskRepo.findAllSlice(pageable)).thenReturn(expectedSlice);

        // Act
        Slice<Task> actualSlice = taskService.findAllSlice(pageable);

        // Assert
        assertEquals(expectedSlice, actualSlice);
        verify(taskRepo, times(1)).findAllSlice(pageable);
    }

    @Test
    public void testSaveTask_Success() throws PersonNotFoundException {
        // Arrange
        Task task = new Task();
        task.setAuthor(Person.builder().email("author@example.com").build());
        task.setPerformer(Person.builder().email("performer@example.com").build());

        Person author = Person.builder().email("author@example.com").build();
        Person performer = Person.builder().email("performer@example.com").build();

        when(personService.findByEmail("author@example.com")).thenReturn(author);
        when(personService.findByEmail("performer@example.com")).thenReturn(performer);

        // Act
        taskService.save(task);

        // Assert
        assertEquals(author, task.getAuthor());
        assertEquals(performer, task.getPerformer());
        assertNotNull(task.getCreationDate());
        verify(taskRepo, times(1)).save(task);
    }

    @Test
    public void testSaveTask_PersonNotFound() {
        // Arrange
        Task task = new Task();
        task.setAuthor(Person.builder().email("author@example.com").build());
        task.setPerformer(Person.builder().email("performer@example.com").build());

        when(personService.findByEmail("author@example.com")).thenThrow(new PersonNotFoundException("author@example.com"));

        // Act & Assert
        assertThrows(PersonNotFoundException.class, () -> taskService.save(task));
        verify(taskRepo, never()).save(any());
    }

    @Test
    public void testFindById_Success() throws TaskNotFoundException {
        // Arrange
        Task task = new Task();
        task.setId(1L);
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task));

        // Act
        Task actualTask = taskService.findById(1L);

        // Assert
        assertEquals(task, actualTask);
        verify(taskRepo, times(1)).findById(1L);
    }

    @Test
    public void testFindById_TaskNotFound() {
        // Arrange
        when(taskRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.findById(1L));
        verify(taskRepo, times(1)).findById(1L);
    }

    @Test
    public void testDeleteTask() {
        // Arrange
        long taskId = 1L;

        // Act
        taskService.delete(taskId);

        // Assert
        verify(taskRepo, times(1)).deleteById(taskId);
    }

    @Test
    public void testFindByAuthorId() {
        // Arrange
        long authorId = 1L;
        Pageable pageable = Pageable.unpaged();
        Slice<Task> expectedSlice = new PageImpl<>(Collections.emptyList());
        when(taskRepo.findByAuthorId(authorId, pageable)).thenReturn(expectedSlice);

        // Act
        Slice<Task> actualSlice = taskService.findByAuthorId(authorId, pageable);

        // Assert
        assertEquals(expectedSlice, actualSlice);
        verify(taskRepo, times(1)).findByAuthorId(authorId, pageable);
    }

    @Test
    public void testFindByPerformerId() {
        // Arrange
        long performerId = 1L;
        Pageable pageable = Pageable.unpaged();
        Slice<Task> expectedSlice = new PageImpl<>(Collections.emptyList());
        when(taskRepo.findByPerformerId(performerId, pageable)).thenReturn(expectedSlice);

        // Act
        Slice<Task> actualSlice = taskService.findByPerformerId(performerId, pageable);

        // Assert
        assertEquals(expectedSlice, actualSlice);
        verify(taskRepo, times(1)).findByPerformerId(performerId, pageable);
    }
}
