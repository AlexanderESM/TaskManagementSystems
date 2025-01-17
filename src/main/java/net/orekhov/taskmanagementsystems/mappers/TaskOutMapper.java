package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.TaskOutDto;
import net.orekhov.taskmanagementsystems.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс для преобразования данных между объектами Task и TaskOutDto.
 * Используется для маппинга данных задачи из базы данных в DTO для отображения внешним пользователям.
 */
@Mapper
public interface TaskOutMapper {

    /**
     * Преобразует объект Task в объект TaskOutDto.
     * @param task объект Task, содержащий данные задачи из базы данных.
     * @return объект TaskOutDto, готовый для передачи внешнему пользователю через API.
     */
    @Mapping(source = "task.author", target = "author")
    @Mapping(source = "task.performer", target = "performer")
    @Mapping(source = "task.comments", target = "comments")
    @Mapping(source = "task.creationDate", target = "creationDate")
    TaskOutDto objToDto(Task task);

}
