package net.orekhov.taskmanagementsystems.models;

import jakarta.persistence.*;
import lombok.*;
import net.orekhov.taskmanagementsystems.enums.Role;

import java.io.Serializable;
import java.util.List;

/**
 * Сущность, представляющая пользователя системы.
 * Хранит информацию о пользователе, его задачах и ролях.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "entities", name="person")
public class Person implements Serializable {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Имя пользователя.
     * Хранится в базе данных в поле "username".
     */
    @Column(name="username")
    private String username;

    /**
     * Электронная почта пользователя.
     * Хранится в базе данных в поле "email".
     */
    @Column(name="email")
    private String email;

    /**
     * Пароль пользователя.
     * Хранится в базе данных в поле "password".
     */
    @Column(name="password")
    private String password;

    /**
     * Роль пользователя в системе (ADMIN/USER).
     * Используется перечисление Role.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Список задач, созданных пользователем.
     * Связь с задачами через внешний ключ "author".
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @Column(name="created_tasks")
    private List<Task> createdTasks;

    /**
     * Список задач, выполняемых пользователем.
     * Связь с задачами через внешний ключ "performer".
     */
    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    @Column(name="performed_tasks")
    private List<Task> performedTasks;

}
