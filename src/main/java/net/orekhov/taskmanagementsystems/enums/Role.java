package net.orekhov.taskmanagementsystems.enums;

/**
 * Перечисление, представляющее роли пользователей в системе.
 * Каждой роли присваивается определенный уровень доступа в системе.
 */
public enum Role {

    /**
     * Роль пользователя, которая имеет базовые права доступа.
     * Обычно это обычные пользователи, которые могут создавать задачи и комментировать их.
     */
    USER,

    /**
     * Роль администратора, которая имеет расширенные права доступа.
     * Администратор может управлять пользователями, задачами и их статусами.
     */
    ADMIN
}
