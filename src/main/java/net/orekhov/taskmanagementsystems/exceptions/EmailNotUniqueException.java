package net.orekhov.taskmanagementsystems.exceptions;

/**
 * Исключение, выбрасываемое, когда попытка зарегистрировать пользователя с уже использованным email.
 * Это исключение сигнализирует о том, что email уже существует в системе.
 */
public class EmailNotUniqueException extends RuntimeException {

    /**
     * Поле, для которого произошло нарушение уникальности (в данном случае это email).
     */
    private String field;

    /**
     * Конструктор исключения, принимающий поле, которое вызвало ошибку.
     * @param field - поле, в котором возникла ошибка (например, email).
     */
    public EmailNotUniqueException(String field) {
        super("Email is already used: ");
        this.field = field;
    }

    /**
     * Получение сообщения об ошибке с указанием поля, которое вызвало исключение.
     * @return Сообщение об ошибке с указанием email.
     */
    public String getErrorMessageWithField(){
        return this.getMessage() + field;
    }
}
