package net.orekhov.taskmanagementsystems.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.orekhov.taskmanagementsystems.enums.TaskPriority;
import net.orekhov.taskmanagementsystems.enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сущность, представляющая задачу в системе.
 * Каждая задача включает заголовок, описание, статус, приоритет,
 * автора, исполнителя, список комментариев и дату создания.
 */
@Entity
@Table(schema = "entities", name = "task")
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {

    /**
     * Уникальный идентификатор задачи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Заголовок задачи.
     */
    @Column(name="header")
    private String header;

    /**
     * Описание задачи.
     */
    @Column(name="description")
    private String description;

    /**
     * Статус задачи (например, "в ожидании", "в процессе", "завершено").
     */
    @Enumerated(EnumType.STRING)
    @Column(name="task_status")
    private TaskStatus taskStatus;

    /**
     * Приоритет задачи (например, "высокий", "средний", "низкий").
     */
    @Enumerated(EnumType.STRING)
    @Column(name="task_priority")
    private TaskPriority taskPriority;

    /**
     * Автор задачи (пользователь, создавший задачу).
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;

    /**
     * Исполнитель задачи (пользователь, выполняющий задачу).
     */
    @ManyToOne
    @JoinColumn(name = "performer_id")
    private Person performer;

    /**
     * Список комментариев, прикрепленных к задаче.
     */
    @OneToMany(mappedBy = "task", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments;

    /**
     * Дата создания задачи.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    // Геттеры и сеттеры для полей

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Person getPerformer() {
        return performer;
    }

    public void setPerformer(Person performer) {
        this.performer = performer;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Переопределение метода toString для отображения информации о задаче.
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskPriority=" + taskPriority +
                ", author=" + author +
                ", performer=" + performer +
                ", comments=" + comments +
                ", creationDate=" + creationDate +
                '}';
    }
}
