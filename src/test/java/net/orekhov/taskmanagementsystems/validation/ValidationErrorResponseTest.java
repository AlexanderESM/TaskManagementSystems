package net.orekhov.taskmanagementsystems.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValidationErrorResponseTest {

    @Test
    void testValidationErrorResponseInitialization() {
        // Arrange
        Violation violation1 = new Violation("field1", "must not be null");
        Violation violation2 = new Violation("field2", "must be a valid email");
        List<Violation> violations = List.of(violation1, violation2);

        // Act
        ValidationErrorResponse response = new ValidationErrorResponse(violations);

        // Assert
        assertNotNull(response.getViolations(), "Violations list should not be null");
        assertEquals(2, response.getViolations().size(), "Violations list should contain 2 items");
        assertEquals("field1", response.getViolations().get(0).getFieldName());
        assertEquals("must not be null", response.getViolations().get(0).getMessage());
        assertEquals("field2", response.getViolations().get(1).getFieldName());
        assertEquals("must be a valid email", response.getViolations().get(1).getMessage());
    }
}
