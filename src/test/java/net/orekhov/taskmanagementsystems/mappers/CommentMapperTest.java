package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.CommentDto;
import net.orekhov.taskmanagementsystems.models.Comment;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    public void testDtoToObj() {
        // Создаем объект CommentDto
        CommentDto commentDto = CommentDto.builder()
                .text("Test comment text")
                .author("Test author")
                .build();

        // Преобразуем в объект Comment
        Comment comment = commentMapper.dtoToObj(commentDto);

        // Проверяем, что поля были корректно перенесены
        assertEquals(commentDto.getText(), comment.getText());
        assertEquals(commentDto.getAuthor(), comment.getAuthor());
    }
}
