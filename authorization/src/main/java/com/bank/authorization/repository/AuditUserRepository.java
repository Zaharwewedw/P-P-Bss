package com.bank.authorization.repository;

import com.bank.authorization.audit.AuditUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditUserRepository extends JpaRepository<AuditUser, Long> {
}
