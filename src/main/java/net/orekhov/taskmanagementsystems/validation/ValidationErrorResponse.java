package net.orekhov.taskmanagementsystems.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Класс для представления ответа об ошибках валидации.
 * Содержит список нарушений (ошибок) валидации, которые могут быть возвращены клиенту.
 */
@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {

    /**
     * Список нарушений (ошибок) валидации, которые должны быть возвращены клиенту.
     * Каждый элемент списка представляет собой ошибку с подробной информацией.
     */
    private final List<Violation> violations;

}
