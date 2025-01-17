package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.PersonDto;
import net.orekhov.taskmanagementsystems.models.Person;
import org.mapstruct.Mapper;

/**
 * Интерфейс для преобразования данных между объектами Person и PersonDto.
 * Используется для маппинга данных из сущностей в DTO для внешних представлений.
 */
@Mapper
public interface PersonMapper {

    /**
     * Преобразует объект Person в объект PersonDto.
     * @param person объект, содержащий данные пользователя из базы данных.
     * @return объект PersonDto, содержащий данные для отображения в ответе API.
     */
    PersonDto objToDto(Person person);

    /**
     * Преобразует объект PersonDto в объект Person.
     * @param personDto объект, содержащий данные пользователя, передаваемого через API.
     * @return объект Person, который можно сохранить в базе данных.
     */
    Person dtoToObj(PersonDto personDto);
}
