package net.orekhov.taskmanagementsystems.security.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.orekhov.taskmanagementsystems.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Реализация интерфейса UserDetails для интеграции с Spring Security.
 * Класс представляет пользователя с дополнительной информацией о его роли.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDetails implements UserDetails {

    /**
     * Модель пользователя (Person), которую этот класс оборачивает.
     */
    private Person person;

    /**
     * Получение пароля пользователя.
     * @return пароль пользователя.
     */
    @Override
    public String getPassword() {
        return person.getPassword();
    }

    /**
     * Получение имени пользователя (в данном случае — email).
     * @return email пользователя.
     */
    @Override
    public String getUsername() {
        return person.getEmail();
    }

    /**
     * Проверка, не истек ли срок действия учетной записи.
     * @return true, так как учетная запись всегда активна.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверка, не заблокирована ли учетная запись.
     * @return true, так как учетная запись всегда доступна.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверка, не истекли ли учетные данные пользователя.
     * @return true, так как учетные данные всегда актуальны.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверка, активен ли пользователь.
     * @return true, так как пользователь всегда активен.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Получение списка прав пользователя.
     * @return список прав (роли), ассоциированных с пользователем.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(person.getRole().name()));
    }
}
