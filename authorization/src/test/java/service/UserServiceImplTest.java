package service;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.Users;
import com.bank.authorization.repository.UserRepository;
import com.bank.authorization.security.UserDetailsServerImpl;
import com.bank.authorization.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDetailsServerImpl userDetailsServer;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @DisplayName("testing delete by id")
    @Test
    void deleteUserTest() {
        Long id = 1L;
        userService.deleteUser(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @DisplayName("testing add user starter")
    @Test
    void addUserStarterTest() {
        String password = "Kata";
        String encodedPassword = "encodedKata";
        int profileId = 12131;
        String role = "ROLE_ADMIN";
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        userService.AddUserStarter();
        verify(passwordEncoder, times(1)).encode(password);
        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        Users savedUser = userCaptor.getValue();
        assertUserProperties(savedUser, encodedPassword, profileId, role);
    }

    private void assertUserProperties(Users user, String expectedPassword, int expectedProfileId, String expectedRole) {
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedProfileId, user.getProfileId());
        assertEquals(expectedRole, user.getRole());
    }

    @DisplayName("testing getting all users from the database")
    @Test
    void getAllUsersTest() {
        List<Users> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);
        List<Users> result = userService.getAllUsers();
        assertTrue(result.isEmpty());
        assertEquals(users, result);
    }
    @DisplayName("testing getting by id user")
    @Test
    void getByIdUserTest () {
        Long userId = 1L;
        Users expectedUser = new Users();
        expectedUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        Users actualUser = userService.getByIdUser(userId);
        verify(userRepository, times(1)).findById(userId);
        assertEquals(expectedUser, actualUser);
    }


    @DisplayName("testing register user")
    @Test
    void registerTest () {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole("userRole");
        userDTO.setProfileId(1);
        userDTO.setPassword("plain_password");
        String encodedPassword = "encoded_password";
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn(encodedPassword);
        userService.register(userDTO);
        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
    }
    @DisplayName("testing up date user")
    @Test
    void upDateUserTest() {
        long userId = 1L;
        Users existingUser = new Users();
        existingUser.setId(userId);
        existingUser.setProfileId(1);
        existingUser.setRole("existingRole");
        existingUser.setPassword("existingPassword");
        when(userDetailsServer.getUserPrincipalByUsername(anyInt())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        UserDTO userDTO = new UserDTO();
        userDTO.setProfileId(2);
        userDTO.setRole("newRole");
        userDTO.setPassword("newPassword");
        userService.upDateUser(userDTO);
        ArgumentCaptor<Users> userArgumentCaptor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        Users savedUser = userArgumentCaptor.getValue();
        assertEquals(userId, savedUser.getId());
        assertEquals(userDTO.getProfileId(), savedUser.getProfileId());
        assertEquals(userDTO.getRole(), savedUser.getRole());
        assertEquals("encodedPassword", savedUser.getPassword());
    }
}
