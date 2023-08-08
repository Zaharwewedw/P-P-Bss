package com.bank.authorization.service;

import com.bank.authorization.audit.AuditUser;

public interface AuditUserService {
    void saveAudit (AuditUser auditUser);

}
