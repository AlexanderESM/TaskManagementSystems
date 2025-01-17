package net.orekhov.taskmanagementsystems.validation;

import jakarta.validation.ConstraintViolationException;
import net.orekhov.taskmanagementsystems.exceptions.EmailNotUniqueException;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import net.orekhov.taskmanagementsystems.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Этот класс предоставляет централизованную обработку ошибок для контроллеров.
 * Используется аннотация @ControllerAdvice, которая позволяет перехватывать исключения,
 * возникающие в контроллерах, и возвращать клиенту правильный ответ с кодом ошибки.
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    /**
     * Обрабатывает ошибки валидации для объектов, аннотированных с @Valid.
     * Например, если в запросе отсутствует обязательное поле.
     *
     * @param e исключение, содержащее список нарушений валидации.
     * @return ответ с детализированным списком нарушений.
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        // Извлекаем нарушения валидации и формируем список нарушений
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        // Возвращаем объект с ошибками валидации
        return new ValidationErrorResponse(violations);
    }

    /**
     * Обрабатывает ошибки валидации полей метода при использовании аннотации @Valid.
     * Например, если переданы неверные значения в теле запроса.
     *
     * @param e исключение, содержащее ошибки привязки полей.
     * @return ответ с детализированным списком ошибок.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        // Извлекаем ошибки привязки полей и формируем список ошибок
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        // Возвращаем объект с ошибками
        return new ValidationErrorResponse(violations);
    }

    /**
     * Обрабатывает исключение, когда email не уникален.
     * В случае ошибки, например, если попытаться зарегистрировать пользователя с уже существующим email.
     *
     * @param e исключение с ошибкой email.
     * @return ответ с ошибкой и полем "email".
     */
    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<Violation> onEmailNotFoundException(
            EmailNotUniqueException e
    ) {
        // Выводим сообщение об ошибке в консоль для отладки
        System.out.println(e.getErrorMessageWithField());
        // Возвращаем ответ с ошибкой и значением поля "email"
        return new ResponseEntity<>(new Violation("email", e.getErrorMessageWithField()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение, когда пользователь не найден.
     * В случае ошибки, например, если запросить информацию о несуществующем пользователе.
     *
     * @param e исключение с ошибкой поиска пользователя.
     * @return ответ с ошибкой и полем "email".
     */
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Violation> onPersonNotFoundException(
            PersonNotFoundException e
    ) {
        // Выводим сообщение об ошибке в консоль для отладки
        System.out.println(e.getErrorMessageWithField());
        // Возвращаем ответ с ошибкой и значением поля "email"
        return new ResponseEntity<>(new Violation("email", e.getErrorMessageWithField()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение, когда задача не найдена.
     * В случае ошибки, например, если запросить информацию о несуществующей задаче.
     *
     * @param e исключение с ошибкой поиска задачи.
     * @return ответ с ошибкой и полем "taskId".
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Violation> onTaskNotFoundException(
            TaskNotFoundException e
    ) {
        // Выводим сообщение об ошибке в консоль для отладки
        System.out.println(e.getMessage());
        // Возвращаем ответ с ошибкой и значением поля "taskId"
        return new ResponseEntity<>(new Violation("taskId", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
