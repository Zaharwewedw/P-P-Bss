package model;

import com.bank.authorization.model.UserAudit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

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

        Assertions.assertEquals(timestamp, userAudit.getModifiedAtImage());
        Assertions.assertEquals(timestamp, userAudit.getCreatedAtImage());

        Assertions.assertEquals("NuN", userAudit.getEntityJsonImage());
        Assertions.assertEquals("NuN", userAudit.getCreatedBy());
        Assertions.assertEquals("NuN", userAudit.getOperationType());
        Assertions.assertEquals("NuN", userAudit.getModifiedBy());
        Assertions.assertEquals("NuN", userAudit.getEntityType());
        Assertions.assertEquals("NuN", userAudit.getNewEntityJsonImage());
        Assertions.assertEquals(0L, userAudit.getId());

    }
}
