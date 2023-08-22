package controller;

import com.bank.authorization.controller.Controller;
import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.Users;
import com.bank.authorization.security.UserDetailsServerImpl;
import com.bank.authorization.service.UserService;
import com.bank.authorization.util.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    @InjectMocks
    private Controller userController;
    @Mock
    private UserDetailsServerImpl userDetailsServer;
    @Mock
    private UserService userService;

    @Mock
    private UserValidator userValidator;

    @DisplayName("testing default user controller")
    @Test
    void getViuTest() {
        Users user1 = new Users();
        user1.setId(1L);
        user1.setProfileId(1);
        user1.setRole("ROLE_USER");
        user1.setPassword("pwd1");
        Users user2 = new Users();
        user2.setId(2L);
        user2.setProfileId(2);
        user2.setRole("ROLE_ADMIN");
        user2.setPassword("pwd2");
        List<Users> usersList = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(usersList);
        ResponseEntity<List<Users>> response = userController.getViu();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertEquals(user1.getId(), response.getBody().get(0).getId());
        assertEquals(user2.getId(), response.getBody().get(1).getId());
    }
    @DisplayName("testing delete user by id controller")
    @Test
    void deleteUserTest() {
        long userId = 1L;
        ResponseEntity<String> response = userController.deleteUser(userId);
        assertEquals("Пользователь успешно удален", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

    @DisplayName("testing get by id user controller")
    @Test
    void getLustIdTest() {
        Long userId = 1L;
        Users user = new Users();
        user.setPassword("password");
        user.setProfileId(2);
        user.setRole("ROLE_USER");

        when(userService.getByIdUser(userId)).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.getLastId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO userDTO = response.getBody();
        assert userDTO != null;
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getProfileId(), userDTO.getProfileId());
        assertEquals(user.getRole(), userDTO.getRole());
    }

    @DisplayName("testing update Page method controller")
    @Test
    void updatePageTest() {
        Long userId = 1L;
        Users user = new Users();
        user.setPassword("password");
        user.setProfileId(2);
        user.setRole("ROLE_USER");
        when(userService.getByIdUser(userId)).thenReturn(user);
        ResponseEntity<UserDTO> response = userController.getLastId(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO userDTO = response.getBody();
        assert userDTO != null;
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getProfileId(), userDTO.getProfileId());
        assertEquals(user.getRole(), userDTO.getRole());
    }

    @DisplayName("testing update method controller by user does not exist")
    @Test
    void testUpdatePageUserDoesNotExist() {
        UserDTO userDTO = new UserDTO();
        userDTO.setProfileId(1);
        BindingResult bindingResult = new BeanPropertyBindingResult(userDTO, "userDto");
        when(userDetailsServer.getUserPrincipalByUsername(userDTO.getProfileId())).thenReturn(empty());
        ResponseEntity<?> response = userController.updatePage(userDTO, bindingResult);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("testing update method controller validation errors")
    void testUpdatePageValidationErrors() {
        UserDTO userDTO = new UserDTO();
        userDTO.setProfileId(2);
        BindingResult bindingResult = new BeanPropertyBindingResult(userDTO, "userDto");
        bindingResult.addError(new FieldError("userDto", "field", "Field error message"));
        when(userDetailsServer.getUserPrincipalByUsername(userDTO.getProfileId())).thenReturn(of(new Users()));
        ResponseEntity<?> response = userController.updatePage(userDTO, bindingResult);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("testing update method controller page success")
    void testUpdatePageSuccess() {
        UserDTO userDTO = new UserDTO();
        userDTO.setProfileId(3);
        BindingResult bindingResult = new BeanPropertyBindingResult(userDTO, "userDto");
        when(userDetailsServer.getUserPrincipalByUsername(userDTO.getProfileId())).thenReturn(of(new Users()));
        ResponseEntity<?> response = userController.updatePage(userDTO, bindingResult);
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).upDateUser(userDTO);
    }

    @Test
    @DisplayName("testing save user method controller validation errors")
    void testUserSaveHasValidationErrors() {
        UserDTO userDTO = new UserDTO();
        userDTO.setProfileId(2);
        BindingResult bindingResult = new BeanPropertyBindingResult(userDTO, "userDto");
        bindingResult.addError(new FieldError("userDto", "field", "Field error message"));
        when(userDetailsServer.getUserPrincipalByUsername(userDTO.getProfileId())).thenReturn(empty());
        ResponseEntity<?> response = userController.userSave(userDTO, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        List<String> expectedErrors = bindingResult.getAllErrors().stream().map(err ->
                        err instanceof FieldError ? ((FieldError) err).getField() + ":" + err.getDefaultMessage() : err.getDefaultMessage())
                .collect(Collectors.toList());
        assertEquals(expectedErrors, response.getBody());
    }

    @Test
    @DisplayName("testing save user method controller save success")
    void testUserSaveSuccess() {
        UserDTO userDTO = new UserDTO();
        userDTO.setProfileId(3);
        BindingResult bindingResult = new BeanPropertyBindingResult(userDTO, "userDto");
        when(userDetailsServer.getUserPrincipalByUsername(userDTO.getProfileId())).thenReturn(empty());
        ResponseEntity<?> response = userController.userSave(userDTO, bindingResult);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).register(userDTO);
    }
}