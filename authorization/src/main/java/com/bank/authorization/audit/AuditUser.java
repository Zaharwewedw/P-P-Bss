package com.bank.authorization.audit;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.UserAudit;
import com.bank.authorization.model.Users;
import com.bank.authorization.service.AuditUserService;
import com.bank.authorization.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Order(10)
public class AuditUser {

    private final UserService userService;
    private final AuditUserService auditUserService;
    @Autowired
    public AuditUser(UserService userService, AuditUserService auditUserService) {
        this.userService = userService;
        this.auditUserService = auditUserService;
    }

    @Before("execution(public void deleteUser(Long))")
    public void auditDelete(JoinPoint joinPoint) {
        UserAudit audit = new UserAudit();
        Users users = userService.getByIdUser(Long.parseLong(Arrays.toString(joinPoint.getArgs()).substring(1, Arrays.toString(joinPoint.getArgs()).length() - 1)));
        audit.setEntityType(String.valueOf(users.getClass()));
        audit.setOperationType("delete");
        audit.setEntityJsonImage(entityJson(users));
        audit.setNewEntityJsonImage("NuN");
        audit.setCreatedAtImage(timePoint());
        audit.setModifiedAtImage(timePoint());
        auditUserService.saveAudit(audit);
    }


    @Around("execution(public void upDateUser(..))")
    public Object auditUpdateUser(ProceedingJoinPoint joinPoint) throws Throwable {
        UserAudit audit = new UserAudit();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof UserDTO user) {
                audit.setEntityType(String.valueOf(user.getClass()));
                audit.setNewEntityJsonImage(entityJson(user));
                audit.setEntityJsonImage(entityJson(user));
            }
        }
        audit.setOperationType("update");
        Object result = joinPoint.proceed();
        audit.setCreatedAtImage(timePoint());
        audit.setModifiedAtImage(timePoint());
        auditUserService.saveAudit(audit);
        return result;
    }

    @Before("execution(public void register(..))")
    public void auditRegister (JoinPoint joinPoint) {
        UserAudit audit = new UserAudit();
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).filter(arg -> arg instanceof UserDTO).map(arg -> (UserDTO) arg).forEach(user -> {
            audit.setEntityType(String.valueOf(user.getClass()));
            audit.setNewEntityJsonImage(entityJson(user));
            audit.setEntityJsonImage(entityJson(user));
        });
        audit.setOperationType("post");
        audit.setNewEntityJsonImage("NuN");
        audit.setCreatedAtImage(timePoint());
        audit.setModifiedAtImage(timePoint());
        auditUserService.saveAudit(audit);
    }

    public Timestamp timePoint () {
        Date date = new Date();
        return  new Timestamp(date.getTime());
    }

    public String entityJson (UserDTO us) {
        return  "{" +
                "  \"role\":" + us.getRole() +
                "  \"profileId\":" + us.getProfileId()+
                "  \"password\":" + us.getPassword() +
                "}";
    }

    public String entityJson (Users us) {
        return "{" +
                "  \"role\":" + us.getRole() +
                "  \"profileId\":" + us.getProfileId()+
                "  \"password\":" + us.getPassword() +
                "}";
    }
}
