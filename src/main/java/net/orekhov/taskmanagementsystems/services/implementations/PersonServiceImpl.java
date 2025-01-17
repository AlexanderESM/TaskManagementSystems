package net.orekhov.taskmanagementsystems.services.implementations;

import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.repositories.PersonRepo;
import net.orekhov.taskmanagementsystems.services.PersonService;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Этот сервис обеспечит возможность получения данных о человеке по его электронной почте,
 * а также обработку ошибки, если человек с такой почтой не найден в базе данных.
 */
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo; // Инициализация репозитория
    }

    /**
     * Находит человека по электронной почте.
     * @param email Электронная почта пользователя.
     * @return Объект Person.
     * @throws PersonNotFoundException Если человек с такой электронной почтой не найден.
     */
    public Person findByEmail(String email) {
        // Используется метод findByEmail из репозитория для поиска пользователя
        return personRepo.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException(email)); // Если не найден, выбрасывается исключение
    }
}
