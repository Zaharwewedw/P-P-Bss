package com.bank.history.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "history", schema = "history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "transfer_audit_id")
    Long transfer_audit_id;
    @Column(name = "profile_audit_id")
    Long profile_audit_id;
    @Column(name = "account_audit_id")
    Long account_audit_id;
    @Column(name = "anti_fraud_audit_id")
    Long anti_fraud_audit_id;
    @Column(name = "public_bank_info_audit_id")
    Long public_bank_info_audit_id;
    @Column(name = "authorization_audit_id")
    Long authorization_audit_id;

}
