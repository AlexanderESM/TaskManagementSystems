package net.orekhov.taskmanagementsystems.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViolationTest {
    @Test
    void testValidationErrorResponseCreation() {
        // Arrange
        Violation violation1 = new Violation("field1", "Field1 is invalid");
        Violation violation2 = new Violation("field2", "Field2 is required");
        List<Violation> violations = List.of(violation1, violation2);

        // Act
        ValidationErrorResponse response = new ValidationErrorResponse(violations);

        // Assert
        assertNotNull(response.getViolations(), "Violations list should not be null");
        assertEquals(2, response.getViolations().size(), "Violations list size should match");
        assertEquals("field1", response.getViolations().get(0).getFieldName(), "First violation field name should match");
        assertEquals("Field1 is invalid", response.getViolations().get(0).getMessage(), "First violation message should match");
        assertEquals("field2", response.getViolations().get(1).getFieldName(), "Second violation field name should match");
        assertEquals("Field2 is required", response.getViolations().get(1).getMessage(), "Second violation message should match");
    }

}