package net.orekhov.taskmanagementsystems.services.implementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.orekhov.taskmanagementsystems.services.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Этот сервис управляет генерацией, проверкой и извлечением данных из JWT токенов,
 * используя секретный ключ для подписи и верификации.
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${secret.key}")
    private static String SECRET_KEY; // Секретный ключ для подписи токенов

    /**
     * Извлекает имя пользователя (subject) из JWT.
     * @param token JWT токен.
     * @return имя пользователя.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Извлекает subject (имя пользователя) из токена
    }

    /**
     * Общий метод для извлечения различных данных из JWT.
     * @param token JWT токен.
     * @param claimsResolver Функция для обработки данных (claims) из токена.
     * @param <T> Тип возвращаемого значения.
     * @return Извлеченное значение.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Извлекаем все claims из токена
        return claimsResolver.apply(claims); // Применяем переданную функцию к claims
    }

    /**
     * Генерирует JWT токен для пользователя.
     * @param userDetails Информация о пользователе.
     * @return JWT токен.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails); // Генерация токена без дополнительных данных
    }

    /**
     * Генерирует JWT токен с дополнительными данными.
     * @param extraClaims Дополнительные данные.
     * @param userDetails Информация о пользователе.
     * @return JWT токен.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Устанавливаем дополнительные данные
                .setSubject(userDetails.getUsername()) // Устанавливаем имя пользователя как subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Устанавливаем время выпуска
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Устанавливаем срок действия токена (24 часа)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Подписываем токен с помощью секретного ключа
                .compact(); // Возвращаем компактный токен
    }

    /**
     * Проверка валидности токена.
     * @param token JWT токен.
     * @param userDetails Информация о пользователе.
     * @return true, если токен валиден; иначе false.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Извлекаем имя пользователя из токена
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); // Проверка на совпадение имени и срок годности токена
    }

    /**
     * Проверка истечения срока действия токена.
     * @param token JWT токен.
     * @return true, если срок действия токена истек.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Проверяем, если дата истечения меньше текущей
    }

    /**
     * Извлечение даты истечения срока действия токена.
     * @param token JWT токен.
     * @return Дата истечения срока действия.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Извлекаем дату истечения из токена
    }

    /**
     * Извлечение всех данных из токена.
     * @param token JWT токен.
     * @return Claims (данные) из токена.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey()) // Устанавливаем ключ для подписи
                .build()
                .parseClaimsJws(token) // Парсим токен и извлекаем claims
                .getBody(); // Возвращаем тело (claims) токена
    }

    /**
     * Получение секретного ключа для подписи токена.
     * @return Секретный ключ для подписи.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Декодируем секретный ключ из base64
        return Keys.hmacShaKeyFor(keyBytes); // Возвращаем ключ для HMAC подписи
    }
}
