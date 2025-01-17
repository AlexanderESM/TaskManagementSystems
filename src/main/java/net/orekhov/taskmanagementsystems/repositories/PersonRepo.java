package net.orekhov.taskmanagementsystems.repositories;

import net.orekhov.taskmanagementsystems.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Person (Пользователь).
 * Содержит методы для выполнения операций с базой данных, связанных с пользователями.
 */
@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

    /**
     * Находит пользователя по его email.
     *
     * @param email Электронная почта пользователя.
     * @return Опционально возвращает найденного пользователя, если он существует.
     */
    Optional<Person> findByEmail(String email);
}
