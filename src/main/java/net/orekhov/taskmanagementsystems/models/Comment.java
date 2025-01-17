package net.orekhov.taskmanagementsystems.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Сущность, представляющая комментарий, связанный с задачей.
 * Хранит информацию о комментарии, авторе и времени создания.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "entities", name="comment")
public class Comment implements Serializable {

    /**
     * Уникальный идентификатор комментария.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Email автора комментария.
     */
    @Column(name = "author_email")
    private String author;

    /**
     * Задача, к которой относится комментарий.
     * Связь с сущностью Task через внешний ключ.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    /**
     * Текст комментария.
     * Используется аннотация @JsonProperty для привязки имени поля к JSON.
     */
    @Column(name = "text")
    @JsonProperty("text")
    private String text;

    /**
     * Дата и время создания комментария.
     * Хранится в базе данных как TIMESTAMP.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    @JsonProperty("creationDate")
    private LocalDateTime creationDate;
}
