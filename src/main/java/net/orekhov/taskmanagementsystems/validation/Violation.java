package net.orekhov.taskmanagementsystems.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс, представляющий одно нарушение валидации.
 * Содержит информацию о поле, которое не прошло валидацию, и соответствующее сообщение об ошибке.
 */
@Getter
@RequiredArgsConstructor
public class Violation {

    /**
     * Название поля, которое нарушает правила валидации.
     */
    private final String fieldName;

    /**
     * Сообщение, объясняющее, почему поле не прошло валидацию.
     */
    private final String message;

}
