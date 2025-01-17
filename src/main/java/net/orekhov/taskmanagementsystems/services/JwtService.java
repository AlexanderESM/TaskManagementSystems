package net.orekhov.taskmanagementsystems.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;


/**
 * интерфейс JwtService задает контракт для работы с JWT (JSON Web Tokens)
 */
public interface JwtService {

    /**
     * Извлечение имени пользователя из токена.
     * @param token JWT токен.
     * @return Имя пользователя.
     */
    String extractUsername(String token);

    /**
     * Извлечение произвольного клайма из токена.
     * @param token JWT токен.
     * @param claimsResolver Функция для извлечения нужного клайма.
     * @param <T> Тип данных клайма.
     * @return Значение клайма.
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Генерация JWT токена на основе деталей пользователя.
     * @param userDetails Детали пользователя.
     * @return JWT токен.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Генерация JWT токена с дополнительными клаймами.
     * @param extraClaims Дополнительные клаймы.
     * @param userDetails Детали пользователя.
     * @return JWT токен.
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Проверка, действителен ли токен для данного пользователя.
     * @param token JWT токен.
     * @param userDetails Детали пользователя.
     * @return true, если токен действителен; false, если нет.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
