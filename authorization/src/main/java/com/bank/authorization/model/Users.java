package com.bank.authorization.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "auth")
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40, message = "The role must contain up to 40 characters")
    private String role;

    @Column(name = "profile_id")
    private int profileId;

    @NotEmpty(message = "The password cannot be empty")
    @Size(max = 500, message = "The password must contain up to 500 characters")
    private String password;

}
