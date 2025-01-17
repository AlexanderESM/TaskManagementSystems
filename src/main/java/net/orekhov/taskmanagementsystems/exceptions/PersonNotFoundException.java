package net.orekhov.taskmanagementsystems.exceptions;

/**
 * Исключение, выбрасываемое, когда человек с указанными учетными данными не найден в системе.
 * Это исключение сигнализирует о том, что попытка найти пользователя по данным (например, по email или id) не удалась.
 */
public class PersonNotFoundException extends RuntimeException {

    /**
     * Поле, которое вызвало ошибку (например, email или id).
     */
    private String field;

    /**
     * Конструктор исключения, принимающий строковое поле (например, email).
     * @param field - поле, которое вызвало ошибку (например, email).
     */
    public PersonNotFoundException(String field) {
        super("Person with this credentials not found: ");
        this.field = field;
    }

    /**
     * Конструктор исключения, принимающий числовое поле (например, id).
     * @param field - поле, которое вызвало ошибку (например, id).
     */
    public PersonNotFoundException(long field) {
        super("Person with this credentials not found: ");
        this.field = String.valueOf(field);
    }

    /**
     * Получение сообщения об ошибке с указанием поля, которое вызвало исключение.
     * @return Сообщение об ошибке с указанием поля (например, email или id).
     */
    public String getErrorMessageWithField(){
        return this.getMessage() + field;
    }
}
