package security;

import com.bank.authorization.model.Users;
import com.bank.authorization.repository.UserRepository;
import com.bank.authorization.security.UserDetailsServerImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserSecurityDetailsRoleTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetails userDetails;
    @InjectMocks
    private UserDetailsServerImpl userDetailsServer;
    @Test
    @DisplayName("testing delete by id")
    void testGetAll () {
        int username = 1;
        Users expectedUser = new Users();
        when(userRepository.getAllByProfileId(username)).thenReturn(Optional.of(expectedUser));
        Optional<Users> result = userDetailsServer.getUserPrincipalByUsername(username);
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepository, times(1)).getAllByProfileId(username);
    }

    @Test
    @DisplayName("testing delete by id")
    void getUserPrincipalByUsername_NotFound() {
        int username = 2;
        when(userRepository.getAllByProfileId(username)).thenReturn(Optional.empty());
        Optional<Users> result = userDetailsServer.getUserPrincipalByUsername(username);

    }
    @Test
    @DisplayName("testing delete by id")
    void loadUserByUsername_UserFound() {
        int profileId = 12131;
        Users user = new Users();
        when(userRepository.getAllByProfileId(profileId)).thenReturn(Optional.of(user));
        userDetails = userDetailsServer.loadUserByUsername(Integer.toString(profileId));
        assertEquals(String.valueOf(user.getProfileId()), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    @DisplayName("testing delete by id")
    void loadUserByUsername_UserNotFound() {
        int nonExistingProfileId = 40404;
        when(userRepository.getAllByProfileId(nonExistingProfileId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServer.loadUserByUsername(Integer.toString(nonExistingProfileId));
        });
    }
}
