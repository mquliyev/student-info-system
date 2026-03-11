package com.example.sis;

import com.example.sis.config.JwtService;
import com.example.sis.dto.AuthenticationRequest;
import com.example.sis.dto.AuthenticationResponse;
import com.example.sis.dto.RegisterRequest;
import com.example.sis.entity.Role;
import com.example.sis.entity.User;
import com.example.sis.repository.UserRepository;
import com.example.sis.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private User user;
    private RegisterRequest registerRequest;
    private AuthenticationRequest authRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstname("Ali")
                .lastname("Hasanov")
                .email("ali@gmail.com")
                .password("encoded_password")
                .role(Role.USER)
                .build();

        registerRequest = RegisterRequest.builder()
                .firstname("Ali")
                .lastname("Hasanov")
                .email("ali@gmail.com")
                .password("123456")
                .build();

        authRequest = AuthenticationRequest.builder()
                .email("ali@gmail.com")
                .password("123456")
                .build();
    }

    @Test
    void register_shouldReturnToken() {
        when(passwordEncoder.encode("123456")).thenReturn("encoded_password");
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("mocked_token");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response.getToken());
        assertEquals("mocked_token", response.getToken());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void register_shouldEncodePassword() {
        when(passwordEncoder.encode("123456")).thenReturn("encoded_password");
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("mocked_token");

        authenticationService.register(registerRequest);

        verify(passwordEncoder, times(1)).encode("123456");
    }

    @Test
    void authenticate_shouldReturnToken() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByEmail("ali@gmail.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("mocked_token");

        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        assertNotNull(response.getToken());
        assertEquals("mocked_token", response.getToken());
    }

    @Test
    void authenticate_shouldCallAuthenticationManager() {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByEmail("ali@gmail.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("mocked_token");

        authenticationService.authenticate(authRequest);

        verify(authenticationManager, times(1)).authenticate(any());
    }
}
