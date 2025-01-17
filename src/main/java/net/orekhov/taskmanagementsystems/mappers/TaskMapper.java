package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.TaskDto;
import net.orekhov.taskmanagementsystems.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс для преобразования данных между объектами Task и TaskDto.
 * Используется для маппинга данных из сущностей задачи в DTO для внешних представлений.
 */
@Mapper
public interface TaskMapper {

    /**
     * Преобразует объект TaskDto в объект Task.
     * @param taskDto объект, содержащий данные задачи, передаваемой через API.
     * @return объект Task, который можно сохранить в базе данных.
     */
    @Mapping(source = "taskDto.author", target = "author")
    @Mapping(source = "taskDto.performer", target = "performer")
    Task dtoToObj(TaskDto taskDto);
}
