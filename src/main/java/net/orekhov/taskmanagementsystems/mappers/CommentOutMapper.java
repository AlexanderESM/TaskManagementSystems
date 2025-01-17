package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.CommentOutDto;
import net.orekhov.taskmanagementsystems.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс для преобразования данных между объектами Comment и CommentOutDto.
 * Используется для маппинга данных из сущностей в DTO для внешних представлений.
 */
@Mapper
public interface CommentOutMapper {

    /**
     * Преобразует объект Comment в объект CommentOutDto.
     * @param comment объект, содержащий данные комментария из базы данных.
     * @return объект CommentOutDto, содержащий данные для отображения в ответе API.
     */
    @Mapping(source = "comment.text", target = "text") // Маппинг поля "text"
    @Mapping(source = "comment.author", target = "author") // Маппинг поля "author"
    @Mapping(source = "comment.creationDate", target = "creationDate") // Маппинг поля "creationDate"
    CommentOutDto objToDto(Comment comment);
}
