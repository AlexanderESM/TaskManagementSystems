package net.orekhov.taskmanagementsystems.services.implementations;

import net.orekhov.taskmanagementsystems.dto.AuthenticationRequest;
import net.orekhov.taskmanagementsystems.dto.AuthenticationResponse;
import net.orekhov.taskmanagementsystems.dto.RegisterRequest;
import net.orekhov.taskmanagementsystems.enums.Role;
import net.orekhov.taskmanagementsystems.exceptions.EmailNotUniqueException;
import net.orekhov.taskmanagementsystems.exceptions.PersonNotFoundException;
import net.orekhov.taskmanagementsystems.models.Person;
import net.orekhov.taskmanagementsystems.repositories.PersonRepo;
import net.orekhov.taskmanagementsystems.security.details.PersonDetails;
import net.orekhov.taskmanagementsystems.services.AuthenticationService;
import net.orekhov.taskmanagementsystems.services.implementations.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {

    @Mock
    private PersonRepo personRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationServiceImpl(personRepo, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    public void testRegister_Success() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "testUser", "password123");

        // Create the person object using the builder pattern
        Person person = Person.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123") // Password is set as plain text here for the test
                .role(Role.USER) // Set the role
                .build();

        PersonDetails personDetails = PersonDetails.builder().person(person).build();

        when(personRepo.findByEmail(registerRequest.getEmail())).thenReturn(java.util.Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(personDetails)).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(personRepo, times(1)).save(any(Person.class));
    }



    @Test
    public void testRegister_EmailNotUnique() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "testUser", "password123");

        when(personRepo.findByEmail(registerRequest.getEmail())).thenReturn(java.util.Optional.of(new Person()));

        // Act & Assert
        assertThrows(EmailNotUniqueException.class, () -> authenticationService.register(registerRequest));
    }

     @Test
    public void testAuthenticate_PersonNotFound() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("nonexistent@example.com", "password123");

        when(personRepo.findByEmail(authRequest.getEmail())).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(PersonNotFoundException.class, () -> authenticationService.authenticate(authRequest));
    }

    @Test
    public void testAuthenticate_Success() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "password123");

        // Create the person object using the builder pattern
        Person person = Person.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123")
                .role(Role.USER)
                .build();  // Use the builder to create the person object

        PersonDetails personDetails = PersonDetails.builder().person(person).build();

        when(personRepo.findByEmail(authRequest.getEmail())).thenReturn(java.util.Optional.of(person));
        when(jwtService.generateToken(personDetails)).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    public void testAuthenticate_Failure() {
        // Arrange
        AuthenticationRequest authRequest = new AuthenticationRequest("test@example.com", "wrongPassword");

        // Create the person object using the builder pattern
        Person person = Person.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123")
                .role(Role.USER)
                .build();  // Use the builder to create the person object

        when(personRepo.findByEmail(authRequest.getEmail())).thenReturn(java.util.Optional.of(person));

        // Act & Assert
        assertThrows(Exception.class, () -> authenticationService.authenticate(authRequest));
    }



}
