package net.orekhov.taskmanagementsystems.services.implementations;

import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.repositories.PersonRepo;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceImplTest {

    private PersonServiceImpl personService;
    private PersonRepo personRepo;

    @BeforeEach
    public void setUp() {
        personRepo = mock(PersonRepo.class); // Мокаем репозиторий
        personService = new PersonServiceImpl(personRepo); // Создаем сервис с замоканным репозиторием
    }

    @Test
    public void testFindByEmail_Found() {
        // Arrange
        String email = "test@example.com";
        Person expectedPerson = new Person();
        expectedPerson.setEmail(email);
        when(personRepo.findByEmail(email)).thenReturn(Optional.of(expectedPerson)); // Мокаем успешный поиск

        // Act
        Person actualPerson = personService.findByEmail(email);

        // Assert
        assertNotNull(actualPerson); // Убедимся, что результат не null
        assertEquals(expectedPerson, actualPerson); // Проверяем, что возвращен корректный объект
        verify(personRepo, times(1)).findByEmail(email); // Проверяем, что метод findByEmail был вызван один раз
    }

    @Test
    public void testFindByEmail_NotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(personRepo.findByEmail(email)).thenReturn(Optional.empty()); // Мокаем отсутствие результата

        // Act & Assert
        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class, () -> {
            personService.findByEmail(email);
        }); // Ожидаем выброс исключения
        assertEquals("Person not found: " + email, exception.getMessage()); // Проверяем сообщение исключения
        verify(personRepo, times(1)).findByEmail(email); // Проверяем, что метод findByEmail был вызван один раз
    }
}
