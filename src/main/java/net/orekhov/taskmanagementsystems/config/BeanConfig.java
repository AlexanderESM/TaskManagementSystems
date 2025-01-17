package net.orekhov.taskmanagementsystems.config;

import net.orekhov.taskmanagementsystems.mappers.CommentMapper;
import net.orekhov.taskmanagementsystems.mappers.TaskMapper;
import net.orekhov.taskmanagementsystems.mappers.TaskOutMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурации для создания бинов мапперов.
 * В этом классе создаются бины для MapStruct мапперов, которые используются для преобразования объектов
 * между различными слоями приложения, например, между сущностями и DTO.
 */
@Configuration
public class BeanConfig {

    /**
     * Создает бин для маппера TaskOutMapper.
     * Этот маппер используется для преобразования сущностей Task в DTO TaskOutDto.
     *
     * @return экземпляр TaskOutMapper.
     */
    @Bean
    public TaskOutMapper taskOutMapper() {
        // Получаем экземпляр маппера с помощью фабричного метода Mappers.getMapper
        return Mappers.getMapper(TaskOutMapper.class);
    }

    /**
     * Создает бин для маппера TaskMapper.
     * Этот маппер используется для преобразования сущностей Task в другие форматы
     *
     * @return экземпляр TaskMapper.
     */
    @Bean
    public TaskMapper taskMapper() {
        // Получаем экземпляр маппера с помощью фабричного метода Mappers.getMapper
        return Mappers.getMapper(TaskMapper.class);
    }

    /**
     * Создает бин для маппера CommentMapper.
     * Этот маппер используется для преобразования сущностей Comment в соответствующие DTO.
     *
     * @return экземпляр CommentMapper.
     */
    @Bean
    public CommentMapper commentMapper() {
        // Получаем экземпляр маппера с помощью фабричного метода Mappers.getMapper
        return Mappers.getMapper(CommentMapper.class);
    }
}
