package util;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.Users;
import com.bank.authorization.util.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {

    @Test
    public void testUserDto () {
        UserValidator userValidator = new UserValidator();;
        assertTrue(userValidator.supports(Users.class));
    }
}
