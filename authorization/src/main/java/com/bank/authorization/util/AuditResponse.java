package com.bank.authorization.util;

import com.bank.authorization.audit.AuditUser;
import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.Users;
import com.bank.authorization.service.AuditUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditResponse {

    private final AuditUserService auditUserService;

    @Autowired
    public AuditResponse(AuditUserService auditUserService) {
        this.auditUserService = auditUserService;
    }

    public AuditUser AuditRes(Users users, UserDTO userDTO, AuditUser auditUser, String operation) {
        auditUser.setEntityType(users.getClass().toString());

        auditUser.setOperationType(operation);

        auditUser.setCreatedBy(String.valueOf(new StringBuilder(String.valueOf(users.getId()))
                .append(users.getRole())
                .append(users.getProfileId())
                .append(users.getPassword())));

        if (operation.equals("creat")){
            auditUser.setModifiedBy("No modify");
            auditUser.createdAt();
        } else if (operation.equals("update")) {
            auditUser.setCreatedBy((String.valueOf(new StringBuilder("До модификации :")
                    .append(userDTO.getRole())
                    .append(userDTO.getProfileId())
                    .append(userDTO.getPassword()))));

            auditUser.setCreatedBy((String.valueOf(new StringBuilder("Модифицированный :")
                    .append(users.getRole())
                    .append(users.getProfileId())
                    .append(users.getPassword()))));

            auditUser.modifiedAt();
        }

        auditUser.newEntityJson(userDTO);
        auditUser.entityJson(users);
        auditUserService.saveAudit(auditUser);
        return auditUser;
    }
}
