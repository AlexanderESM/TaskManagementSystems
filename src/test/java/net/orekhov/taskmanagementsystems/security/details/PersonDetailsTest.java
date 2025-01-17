package net.orekhov.taskmanagementsystems.security.details;
import net.orekhov.taskmanagementsystems.enums.Role;
import net.orekhov.taskmanagementsystems.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

public class PersonDetailsTest {

    @Mock
    private Person person;

    private PersonDetails personDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personDetails = new PersonDetails(person);
    }

    @Test
    public void testGetPassword() {
        // Arrange
        String password = "password123";
        Mockito.when(person.getPassword()).thenReturn(password);

        // Act
        String result = personDetails.getPassword();

        // Assert
        assertEquals(password, result);
    }

    @Test
    public void testGetUsername() {
        // Arrange
        String email = "test@example.com";
        Mockito.when(person.getEmail()).thenReturn(email);

        // Act
        String result = personDetails.getUsername();

        // Assert
        assertEquals(email, result);
    }

    @Test
    public void testIsAccountNonExpired() {
        // Act
        boolean result = personDetails.isAccountNonExpired();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsAccountNonLocked() {
        // Act
        boolean result = personDetails.isAccountNonLocked();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsCredentialsNonExpired() {
        // Act
        boolean result = personDetails.isCredentialsNonExpired();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsEnabled() {
        // Act
        boolean result = personDetails.isEnabled();

        // Assert
        assertTrue(result);
    }

    @Test
    public void testGetAuthorities() {
        // Arrange
        Role role = Role.USER; // Example role
        Mockito.when(person.getRole()).thenReturn(role);

        // Act
        Collection<? extends GrantedAuthority> authorities = personDetails.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(role.name())));
    }
}
