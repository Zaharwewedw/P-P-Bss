package service;

import com.bank.authorization.model.UserAudit;
import com.bank.authorization.repository.AuditUserRepository;
import com.bank.authorization.service.AuditUserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuditUserServiceImplTest {
    @Mock
    private AuditUserRepository auditUserRepository;

    @InjectMocks
    private AuditUserServiceImpl auditUserServiceImplTest;

    @DisplayName("testing save audit")
    @Test
    void saveAuditTest() {
        UserAudit userAudit = getUserAudit();
        auditUserServiceImplTest.saveAudit(userAudit);  // Ensure you call the save method in the service
        ArgumentCaptor<UserAudit> userAuditCaptor = ArgumentCaptor.forClass(UserAudit.class);
        verify(auditUserRepository, times(1)).save(userAuditCaptor.capture());
        assertEquals(userAudit, userAuditCaptor.getValue());
    }

    private static UserAudit getUserAudit() {
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
        return userAudit;
    }
}
