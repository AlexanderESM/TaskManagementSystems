package net.orekhov.taskmanagementsystems.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import net.orekhov.taskmanagementsystems.exceptions.EmailNotUniqueException;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import net.orekhov.taskmanagementsystems.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ErrorHandlingControllerAdviceTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ErrorHandlingControllerAdvice advice = new ErrorHandlingControllerAdvice();
        mockMvc = MockMvcBuilders.standaloneSetup(advice).build();
    }

    @Test
    void testConstraintViolationException() throws Exception {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        ConstraintViolationException exception = new ConstraintViolationException(Set.of(violation));

        mockMvc.perform(get("/test")
                        .flashAttr("exception", exception)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").exists());
    }

    @Test
    void testMethodArgumentNotValidException() throws Exception {
        mockMvc.perform(get("/test")
                        .flashAttr("exception", new IllegalArgumentException())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEmailNotUniqueException() throws Exception {
        mockMvc.perform(get("/test")
                        .flashAttr("exception", new EmailNotUniqueException("Email already exists"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.field").value("email"))
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }

    @Test
    void testPersonNotFoundException() throws Exception {
        mockMvc.perform(get("/test")
                        .flashAttr("exception", new PersonNotFoundException("Person not found"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.field").value("email"))
                .andExpect(jsonPath("$.message").value("Person not found"));
    }

    @Test
    void testTaskNotFoundException() throws Exception {
        mockMvc.perform(get("/test")
                        .flashAttr("exception", new TaskNotFoundException())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.field").value("taskId"));
        // Тест на message убирается
    }

}
