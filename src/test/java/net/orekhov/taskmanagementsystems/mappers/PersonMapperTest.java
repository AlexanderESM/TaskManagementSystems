package net.orekhov.taskmanagementsystems.mappers;

import net.orekhov.taskmanagementsystems.dto.PersonDto;
import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.enums.Role;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonMapperTest {

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    @Test
    public void testObjToDto() {
        // Создаем объект Person с помощью билдера
        Person person = Person.builder()
                .id(1L)
                .username("John Doe")  // Имя пользователя
                .email("johndoe@example.com")
                .password("password123")
                .role(Role.USER)  // Роль пользователя
                .build();

        // Преобразуем в объект PersonDto
        PersonDto personDto = personMapper.objToDto(person);

        // Проверяем, что email был корректно перенесен
        assertEquals(person.getEmail(), personDto.getEmail());
    }

    @Test
    public void testDtoToObj() {
        // Создаем объект PersonDto
        PersonDto personDto = PersonDto.builder()
                .email("janedoe@example.com")
                .build();

        // Преобразуем в объект Person
        Person person = personMapper.dtoToObj(personDto);

        // Проверяем, что email был корректно перенесен
        assertEquals(personDto.getEmail(), person.getEmail());
    }
}
