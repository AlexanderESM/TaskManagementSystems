package net.orekhov.taskmanagementsystems.services;

import net.orekhov.taskmanagementsystems.dto.AuthenticationRequest;
import net.orekhov.taskmanagementsystems.dto.AuthenticationResponse;
import net.orekhov.taskmanagementsystems.dto.RegisterRequest;

/**
 * интерфейс AuthenticationService задает контракт для сервисов аутентификации
 * и регистрации пользователей
 */

public interface AuthenticationService {

    /**
     * Регистрация нового пользователя.
     * @param request Данные для регистрации.
     * @return Ответ с токеном аутентификации.
     */
    AuthenticationResponse register(RegisterRequest request);

    /**
     * Аутентификация пользователя.
     * @param request Данные для аутентификации.
     * @return Ответ с токеном аутентификации.
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
