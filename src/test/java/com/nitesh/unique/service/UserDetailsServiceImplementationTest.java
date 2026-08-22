package com.nitesh.unique.service;

import com.nitesh.unique.entity.UserEntry;
import com.nitesh.unique.repository.UserRepository;
import com.nitesh.unique.services.UserDetailServiceImplementation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplementationTest {

    @InjectMocks
    private UserDetailServiceImplementation userDetailServiceImplementation;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUserNameTest() {

        // Arrange
        UserEntry user = new UserEntry();
                user.setUserName("ram");
                user.setPassword("htyhty65j");
        user.setRoles(List.of("USER"));


        when(userRepository.findByUserName(anyString()))
                .thenReturn(user);

        UserDetails result =
                userDetailServiceImplementation.loadUserByUsername("ram");


        assertEquals("ram", result.getUsername());

        verify(userRepository).findByUserName("ram");
    }
}