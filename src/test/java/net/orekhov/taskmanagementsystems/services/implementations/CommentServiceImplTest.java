package net.orekhov.taskmanagementsystems.services.implementations;

import net.orekhov.taskmanagementsystems.models.Comment;
import net.orekhov.taskmanagementsystems.repositories.CommentRepo;
import net.orekhov.taskmanagementsystems.services.implementations.CommentServiceImpl;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @Mock
    private CommentRepo commentRepo;

    private CommentServiceImpl commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(commentRepo);
    }

    @Test
    public void testSave_Success() {
        // Arrange
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Test Comment");

        // Act
        commentService.save(comment);

        // Assert
        verify(commentRepo, times(1)).save(comment);
        assertNotNull(comment.getCreationDate()); // Check if the creation date is set
    }

    @Test
    public void testDelete_Success() {
        // Arrange
        long commentId = 1L;

        // Act
        commentService.delete(commentId);

        // Assert
        verify(commentRepo, times(1)).deleteById(commentId);
    }

    @Test
    public void testFindAuthorEmailById_Success() {
        // Arrange
        long commentId = 1L;
        String email = "author@example.com";

        when(commentRepo.findAuthorEmailById(commentId)).thenReturn(java.util.Optional.of(email));

        // Act
        String result = commentService.findAuthorEmailById(commentId);

        // Assert
        assertEquals(email, result);
    }

    @Test
    public void testFindAuthorEmailById_ThrowsException() {
        // Arrange
        long commentId = 1L;

        when(commentRepo.findAuthorEmailById(commentId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(PersonNotFoundException.class, () -> commentService.findAuthorEmailById(commentId));
    }
}
