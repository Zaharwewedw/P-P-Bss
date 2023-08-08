package com.bank.authorization.service;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.Users;

import java.util.List;

public interface UserService {

    List<Users> getAllUsers();

    void upDateUser(UserDTO user);

    void deleteUser(Long id);

    Users getByIdUser(Long id);
    void AddUserStarter();

    void register(UserDTO user);
}
