package com.bank.authorization.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "audit", schema = "auth")
@Getter
@Setter
@NoArgsConstructor
public class UserAudit {

    @Column(name = "entity_type")
    private String entityType;
    @Column(name = "operation_type")
    private String operationType;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at")
    private Timestamp createdAtImage;
    @Column(name = "modified_at")
    private Timestamp modifiedAtImage;
    @Column(name = "new_entity_json")
    private String newEntityJsonImage;
    @Column(name = "entity_json")
    private String entityJsonImage;
}
