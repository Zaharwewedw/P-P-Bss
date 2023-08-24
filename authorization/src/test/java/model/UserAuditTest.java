package model;

import com.bank.authorization.model.UserAudit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAuditTest {

    @Test
    public void testUserAudit () {

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        UserAudit userAudit = new UserAudit();

        userAudit.setModifiedAtImage(timestamp);
        userAudit.setId(0L);
        userAudit.setEntityJsonImage("NuN");
        userAudit.setCreatedBy("NuN");
        userAudit.setOperationType("NuN");
        userAudit.setModifiedBy("NuN");
        userAudit.setEntityType("NuN");
        userAudit.setNewEntityJsonImage("NuN");
        userAudit.setCreatedAtImage(timestamp);

        assertEquals(timestamp, userAudit.getModifiedAtImage());
        assertEquals(timestamp, userAudit.getCreatedAtImage());

        assertEquals("NuN", userAudit.getEntityJsonImage());
        assertEquals("NuN", userAudit.getCreatedBy());
        assertEquals("NuN", userAudit.getOperationType());
        assertEquals("NuN", userAudit.getModifiedBy());
        assertEquals("NuN", userAudit.getEntityType());
        assertEquals("NuN", userAudit.getNewEntityJsonImage());
        assertEquals(0L, userAudit.getId());

    }
}
