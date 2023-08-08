package com.bank.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Size(max = 40, message = "The role must contain up to 40 characters")
    private String role;

    private int profileId;

    @NotEmpty(message = "The password cannot be empty")
    @Size(max = 500, message = "The password must contain up to 500 characters")
    private String password;
}