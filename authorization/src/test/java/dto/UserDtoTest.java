package dto;

import com.bank.authorization.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    @Test
    public void testUserDto () {
        UserDTO userDTO = new UserDTO();

        userDTO.setPassword("123");
        userDTO.setProfileId(1);
        userDTO.setRole("ROLE_ADMIN");

        Assertions.assertEquals("123", userDTO.getPassword());
        Assertions.assertEquals(1, userDTO.getProfileId());
        Assertions.assertEquals("ROLE_ADMIN", userDTO.getRole());

    }
}
