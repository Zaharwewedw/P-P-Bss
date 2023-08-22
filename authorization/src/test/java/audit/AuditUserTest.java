package audit;

import com.bank.authorization.audit.AuditUser;
import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.UserAudit;
import com.bank.authorization.model.Users;
import com.bank.authorization.service.AuditUserServiceImpl;
import com.bank.authorization.service.UserServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditUserTest {
    @InjectMocks
    private AuditUser auditUser;

    @Mock
    private AuditUserServiceImpl auditUserService;

    @Mock
    private ProceedingJoinPoint proceedingjoinPoint;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private JoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        auditUser = new AuditUser(userService, auditUserService);
    }

    @DisplayName("test audit method entityJson")
    @Test
    void testAuditMethodEntityJson () {
        String expectedJson = "{"
                + "  \"role\":user"
                + "  \"profileId\":1"
                + "  \"password\":password"
                + "}";
        UserDTO user1 = new UserDTO();
        user1.setProfileId(1);
        user1.setRole("user");
        user1.setPassword("password");

        assertEquals(expectedJson, auditUser.entityJson(user1));
        Users user2 = new Users();
        user2.setProfileId(1);
        user2.setRole("user");
        user2.setPassword("password");

        assertEquals(expectedJson, auditUser.entityJson(user2));
    }

    @DisplayName("test audit method timePoint")
    @Test
    void testAuditMethodTimePoint  () {
        Timestamp test = auditUser.timePoint();
        assertEquals(Timestamp.class, test.getClass());
    }

    @DisplayName("test audit method AuditDelete")
    @Test
    void testAuditDelete() {
        Long userId = 1L;
        Users testUser = new Users();
        testUser.setId(userId);
        testUser.setRole("ROLE_USER");
        when(userService.getByIdUser(userId)).thenReturn(testUser);
        Object[] args = new Object[]{userId};
        when(joinPoint.getArgs()).thenReturn(args);
        auditUser.auditDelete(joinPoint);
        ArgumentCaptor<UserAudit> auditCaptor = forClass(UserAudit.class);
        verify(auditUserService).saveAudit(auditCaptor.capture());
        UserAudit capturedAudit = auditCaptor.getValue();
        assertEquals(testUser.getClass().toString(), capturedAudit.getEntityType());
        assertEquals("delete", capturedAudit.getOperationType());
        assertNotNull(capturedAudit.getEntityJsonImage());
        assertEquals("NuN", capturedAudit.getNewEntityJsonImage());
        Timestamp auditCreatedTimestamp = capturedAudit.getCreatedAtImage();
        Timestamp auditModifiedTimestamp = capturedAudit.getModifiedAtImage();
        Instant auditCreatedInstant = auditCreatedTimestamp.toInstant();
        Instant auditModifiedInstant = auditModifiedTimestamp.toInstant();
        Instant now = Instant.now();
        assertTrue(auditCreatedInstant.isBefore(now) && auditCreatedInstant.isAfter(now.minusSeconds(5)),
                "The audit created timestamp should be after 5 seconds ago and before the current time");
        assertTrue(auditModifiedInstant.isBefore(now) && auditModifiedInstant.isAfter(now.minusSeconds(5)),
                "The audit modified timestamp should be after 5 seconds ago and before the current time");
    }
    @DisplayName("test audit method UpdateUser")
    @Test
    void testAuditUpdateUser() throws Throwable {
        UserDTO testUser = new UserDTO();
        testUser.setRole("ROLE_USER");
        when(proceedingjoinPoint.getArgs()).thenReturn(new Object[]{testUser});
        when(proceedingjoinPoint.proceed()).thenReturn(true);
        Object result = auditUser.auditUpdateUser(proceedingjoinPoint);
        assertTrue((Boolean)result, "Expected update user method to result in true");
        ArgumentCaptor<UserAudit> auditCaptor = forClass(UserAudit.class);
        verify(auditUserService).saveAudit(auditCaptor.capture());
        UserAudit capturedAudit = auditCaptor.getValue();
        assertEquals(testUser.getClass().toString(), capturedAudit.getEntityType());
        assertEquals("update", capturedAudit.getOperationType());
        assertNotNull(capturedAudit.getEntityJsonImage());
        assertNotNull(capturedAudit.getNewEntityJsonImage());
        Timestamp auditCreatedTimestamp = capturedAudit.getCreatedAtImage();
        Timestamp auditModifiedTimestamp = capturedAudit.getModifiedAtImage();
        Instant auditCreatedInstant = auditCreatedTimestamp.toInstant();
        Instant auditModifiedInstant = auditModifiedTimestamp.toInstant();
        Instant now = Instant.now();
        assertTrue(auditCreatedInstant.isBefore(now) && auditCreatedInstant.isAfter(now.minusSeconds(5)),
                "The audit created timestamp should be after 5 seconds ago and before the current time");
        assertTrue(auditModifiedInstant.isBefore(now) && auditModifiedInstant.isAfter(now.minusSeconds(5)),
                "The audit modified timestamp should be after 5 seconds ago and before the current time");
    }

    @DisplayName("test audit method auditRegister")
    @Test
    void testAuditAuditRegister() {
        UserDTO testUser = new UserDTO();
        testUser.setRole("ROLE_USER");
        when(joinPoint.getArgs()).thenReturn(new Object[]{testUser});
        auditUser.auditRegister(joinPoint);
        ArgumentCaptor<UserAudit> auditCaptor = forClass(UserAudit.class);
        verify(auditUserService).saveAudit(auditCaptor.capture());
        UserAudit capturedAudit = auditCaptor.getValue();
        assertEquals(testUser.getClass().toString(), capturedAudit.getEntityType());
        assertEquals("post", capturedAudit.getOperationType());
        assertNotNull(capturedAudit.getEntityJsonImage());
        assertNotNull(capturedAudit.getNewEntityJsonImage());
        Timestamp auditCreatedTimestamp = capturedAudit.getCreatedAtImage();
        Timestamp auditModifiedTimestamp = capturedAudit.getModifiedAtImage();
        Instant auditCreatedInstant = auditCreatedTimestamp.toInstant();
        Instant auditModifiedInstant = auditModifiedTimestamp.toInstant();
        Instant now = Instant.now();
        assertTrue(auditCreatedInstant.isBefore(now) && auditCreatedInstant.isAfter(now.minusSeconds(5)),
                "The audit created timestamp should be after 5 seconds ago and before the current time");
        assertTrue(auditModifiedInstant.isBefore(now) && auditModifiedInstant.isAfter(now.minusSeconds(5)),
                "The audit modified timestamp should be after 5 seconds ago and before the current time");

    }
}
