package com.bank.authorization.service;

import com.bank.authorization.model.UserAudit;

public interface AuditUserService {
    void saveAudit (UserAudit auditUser);

}
