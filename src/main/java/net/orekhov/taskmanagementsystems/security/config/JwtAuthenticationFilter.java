package net.orekhov.taskmanagementsystems.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.orekhov.taskmanagementsystems.services.implementations.JwtServiceImpl;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр для аутентификации пользователей на основе JWT токенов.
 * Проверяет наличие JWT в заголовке запроса и, если токен валиден,
 * устанавливает пользователя в контекст безопасности.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Сервис для работы с JWT токенами.
     */
    private final JwtServiceImpl jwtService;

    /**
     * Сервис для получения данных пользователя.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Метод, который перехватывает запросы и выполняет проверку JWT токена.
     * Если токен валиден, устанавливает аутентификацию в контексте безопасности.
     *
     * @param request Запрос от клиента.
     * @param response Ответ, который будет отправлен клиенту.
     * @param filterChain Цепочка фильтров, через которые должен пройти запрос.
     * @throws ServletException В случае ошибки при обработке запроса.
     * @throws IOException В случае ошибки при обработке ввода/вывода.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Получаем заголовок Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Если заголовок отсутствует или не начинается с "Bearer ", пропускаем фильтр
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Извлекаем JWT токен из заголовка
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        // Если пользователь не авторизован, пытаемся аутентифицировать его на основе токена
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Загружаем данные пользователя по email
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Если токен валиден, создаем объект аутентификации
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Устанавливаем дополнительные детали аутентификации (например, IP-адрес клиента)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Устанавливаем аутентификацию в контекст безопасности
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Пропускаем запрос через цепочку фильтров
        filterChain.doFilter(request, response);
    }
}
