package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.CommentDto;
import net.orekhov.taskmanagementsystems.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс для преобразования данных между объектами CommentDto и Comment.
 * Используется для маппинга данных из DTO в сущности и наоборот.
 */
@Mapper
public interface CommentMapper {

    /**
     * Преобразует объект CommentDto в объект Comment.
     * @param commentDto объект, содержащий данные комментария.
     * @return объект Comment, содержащий данные для сохранения в базе данных.
     */
    @Mapping(source = "commentDto.text", target = "text") // Маппинг поля "text"
    @Mapping(source = "commentDto.author", target = "author") // Маппинг поля "author"
    Comment dtoToObj(CommentDto commentDto);
}
