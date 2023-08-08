package com.bank.authorization.service;

import com.bank.authorization.audit.AuditUser;
import com.bank.authorization.repository.AuditUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuditUserServiceImpl implements AuditUserService {

    private final AuditUserRepository auditUserRepository;
    @Autowired
    public AuditUserServiceImpl(AuditUserRepository auditUserRepository) {
        this.auditUserRepository = auditUserRepository;
    }

    @Transactional
    @Override
    public void saveAudit(AuditUser auditUser) {
        auditUserRepository.save(auditUser);
    }
}
