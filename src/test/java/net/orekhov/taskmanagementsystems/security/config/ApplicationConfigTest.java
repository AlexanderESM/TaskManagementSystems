package net.orekhov.taskmanagementsystems.security.config;

import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.repositories.PersonRepo;
import net.orekhov.taskmanagementsystems.security.details.PersonDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationConfigTest {

    @Mock
    private PersonRepo personRepo;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    private Person person;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new Person();
        person.setEmail("test@example.com");
        person.setPassword("password123");
    }

    @Test
    public void testUserDetailsService_UserFound() {
        // Arrange
        when(personRepo.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(person));

        // Act
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        PersonDetails personDetails = (PersonDetails) userDetailsService.loadUserByUsername("test@example.com");

        // Assert
        assertNotNull(personDetails);
        assertEquals(person.getEmail(), personDetails.getPerson().getEmail());
    }

    @Test
    public void testUserDetailsService_UserNotFound() {
        // Arrange
        when(personRepo.findByEmail("nonexistent@example.com")).thenReturn(java.util.Optional.empty());

        // Act & Assert
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("nonexistent@example.com"));
    }

    @Test
    public void testAuthenticationProvider() {
        // Act
        AuthenticationProvider authenticationProvider = applicationConfig.authenticationProvider();

        // Assert
        assertNotNull(authenticationProvider);
        assertTrue(authenticationProvider instanceof org.springframework.security.authentication.dao.DaoAuthenticationProvider);
    }

    @Test
    public void testPasswordEncoder() {
        // Act
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        // Arrange
        AuthenticationManager authenticationManager = applicationConfig.authenticationManager(null);

        // Assert
        assertNotNull(authenticationManager);
    }
}
