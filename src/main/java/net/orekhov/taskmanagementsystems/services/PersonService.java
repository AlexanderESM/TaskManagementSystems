package net.orekhov.taskmanagementsystems.services;

import net.orekhov.taskmanagementsystems.models.Person;

public interface PersonService {
    /**
     * Поиск человека по адресу электронной почты.
     * @param email Адрес электронной почты.
     * @return Объект Person, если найден.
     */
    Person findByEmail(String email);
}
