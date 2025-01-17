package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.CommentOutDto;
import net.orekhov.taskmanagementsystems.models.Comment;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentOutMapperTest {

    private final CommentOutMapper commentOutMapper = Mappers.getMapper(CommentOutMapper.class);

    @Test
    public void testObjToDto() {
        // Создаем объект Comment
        Comment comment = Comment.builder()
                .text("Test comment text")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .build();

        // Преобразуем в объект CommentOutDto
        CommentOutDto commentOutDto = commentOutMapper.objToDto(comment);

        // Проверяем, что поля были корректно перенесены
        assertEquals(comment.getText(), commentOutDto.getText());
        assertEquals(comment.getAuthor(), commentOutDto.getAuthor());
        assertEquals(comment.getCreationDate(), commentOutDto.getCreationDate());
    }
}
