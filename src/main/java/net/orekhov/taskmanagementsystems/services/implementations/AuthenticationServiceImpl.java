package net.orekhov.taskmanagementsystems.services.implementations;

import lombok.RequiredArgsConstructor;
import net.orekhov.taskmanagementsystems.enums.Role;
import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.repositories.PersonRepo;
import net.orekhov.taskmanagementsystems.security.details.PersonDetails;
import net.orekhov.taskmanagementsystems.dto.AuthenticationRequest;
import net.orekhov.taskmanagementsystems.dto.AuthenticationResponse;
import net.orekhov.taskmanagementsystems.dto.RegisterRequest;
import net.orekhov.taskmanagementsystems.services.AuthenticationService;
import net.orekhov.taskmanagementsystems.exceptions.EmailNotUniqueException;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
Этот сервис реализует основную логику регистрации и аутентификации пользователей,
интегрируясь с системой безопасности Spring Security.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PersonRepo repository; // Репозиторий для работы с сущностью Person
    private final PasswordEncoder passwordEncoder; // Шифровщик паролей
    private final JwtServiceImpl jwtService; // Сервис для работы с JWT токенами
    private final AuthenticationManager authenticationManager; // Менеджер для аутентификации

    /**
     * Регистрация нового пользователя.
     * @param request запрос на регистрацию, содержащий email, username и password.
     * @return объект AuthenticationResponse с JWT токеном.
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // Проверяем, существует ли уже пользователь с таким email
        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailNotUniqueException(request.getEmail()); // Если email занят, выбрасываем исключение
        }

        // Создаем нового пользователя и сохраняем его в базе данных
        var userDetails =
                PersonDetails.builder()
                        .person(Person.builder()
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword())) // Шифруем пароль
                                .role(Role.USER) // Присваиваем роль пользователя по умолчанию
                                .build())
                        .build();
        repository.save(userDetails.getPerson()); // Сохраняем пользователя в базе данных

        // Генерируем JWT токен для нового пользователя
        var jwtToken = jwtService.generateToken(userDetails);

        // Возвращаем токен в ответе
        return new AuthenticationResponse(jwtToken);
    }

    /**
     * Аутентификация пользователя.
     * @param request запрос на аутентификацию, содержащий email и password.
     * @return объект AuthenticationResponse с JWT токеном.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Находим пользователя по email
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new PersonNotFoundException(request.getEmail())); // Если пользователь не найден, выбрасываем исключение

        // Аутентифицируем пользователя с помощью AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Генерируем JWT токен для аутентифицированного пользователя
        var jwtToken = jwtService.generateToken(PersonDetails.builder()
                .person(user)
                .build());

        // Возвращаем токен в ответе
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
