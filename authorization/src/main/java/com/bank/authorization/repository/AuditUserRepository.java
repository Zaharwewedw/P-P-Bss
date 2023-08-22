package com.bank.authorization.repository;

import com.bank.authorization.model.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditUserRepository extends JpaRepository<UserAudit, Long> {
}
