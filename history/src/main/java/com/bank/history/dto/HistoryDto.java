package com.bank.history.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryDto {
    Long id;
    Long transfer_audit_id;
    Long profile_audit_id;
    Long account_audit_id;
    Long anti_fraud_audit_id;
    Long public_bank_info_audit_id;
    Long authorization_audit_id;
}
