package model;

import com.bank.authorization.model.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestUsers {

    @Test
    public void testUsers() {
        Users users = new Users();
        users.setPassword("123");
        users.setId(1L);
        users.setRole("dad");
        users.setProfileId(123);

        Assertions.assertEquals("dad", users.getRole());
        Assertions.assertEquals("123", users.getPassword());
        Assertions.assertEquals(123, users.getProfileId());
        Assertions.assertEquals(1L, users.getId());
    }
}
