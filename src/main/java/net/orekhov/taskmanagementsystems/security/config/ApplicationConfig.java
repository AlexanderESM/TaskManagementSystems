package net.orekhov.taskmanagementsystems.security.config;

import lombok.RequiredArgsConstructor;
import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.repositories.PersonRepo;
import net.orekhov.taskmanagementsystems.security.details.PersonDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурация безопасности приложения, в том числе настройка аутентификации пользователей.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /**
     * Репозиторий для работы с сущностью Person.
     */
    private final PersonRepo repository;

    /**
     * Бин для получения информации о пользователе на основе email.
     * Используется для аутентификации и авторизации пользователей.
     *
     * @return UserDetailsService для аутентификации пользователей.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Ищем пользователя по email
            Person person = repository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            // Возвращаем объект, реализующий интерфейс UserDetails
            return PersonDetails.builder()
                    .person(person)
                    .build();
        };
    }

    /**
     * Бин для конфигурации AuthenticationProvider, использующего DaoAuthenticationProvider.
     * Этот компонент отвечает за аутентификацию с использованием UserDetailsService и PasswordEncoder.
     *
     * @return AuthenticationProvider, который используется для аутентификации пользователей.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Устанавливаем сервис для получения данных пользователя
        authProvider.setUserDetailsService(userDetailsService());
        // Устанавливаем энкодер паролей
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Бин для AuthenticationManager, который используется для выполнения аутентификации.
     *
     * @param config Конфигурация аутентификации.
     * @return AuthenticationManager, отвечающий за аутентификацию.
     * @throws Exception В случае ошибки конфигурации.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Бин для конфигурации PasswordEncoder, используемого для хеширования паролей.
     * В данном случае используется BCrypt для кодирования паролей.
     *
     * @return PasswordEncoder, используемый для шифрования паролей.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
