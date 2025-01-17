package net.orekhov.taskmanagementsystems.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности для приложения.
 * Настроены фильтры безопасности, разрешенные запросы и механизмы аутентификации.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    /**
     * Фильтр для аутентификации с использованием JWT.
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Поставщик аутентификации для обработки логики аутентификации.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Бин для настройки фильтрации безопасности HTTP запросов.
     * Конфигурирует разрешенные и защищенные маршруты, а также добавляет фильтр для JWT аутентификации.
     *
     * @param http Объект конфигурации безопасности HTTP.
     * @return Отфильтрованный объект HTTP безопасности.
     * @throws Exception В случае ошибки при настройке безопасности.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем защиту от CSRF (не требуется для API, использующих JWT)
                .csrf()
                .disable()

                // Настройка разрешений для URL
                .authorizeHttpRequests()
                // Разрешаем доступ к авторизации и Swagger без аутентификации
                .requestMatchers("/auth/**", "/swagger-ui/index.html", "swagger-ui/**", "/v3/api-docs/**")
                .permitAll()

                // Все остальные запросы требуют аутентификации
                .anyRequest()
                .authenticated()

                // Устанавливаем политику создания сессий: без состояния (для API с JWT)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // Указываем кастомный поставщик аутентификации
                .and()
                .authenticationProvider(authenticationProvider)

                // Добавляем фильтр JWT аутентификации перед фильтром аутентификации по имени и паролю
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Возвращаем построенную конфигурацию безопасности
        return http.build();
    }
}
