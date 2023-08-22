package com.bank.authorization.service;

import com.bank.authorization.audit.AuditUser;
import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.Users;
import com.bank.authorization.repository.UserRepository;
import com.bank.authorization.security.UserDetailsServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsServerImpl userDetailsServer;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           UserDetailsServerImpl userDetailsServer) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDetailsServer = userDetailsServer;
    }

    @Transactional
    @Override
    @PostConstruct
    public void AddUserStarter() {
        Users user = new Users();
        System.out.println(user.getClass());
        user.setPassword(passwordEncoder.encode("Kata"));
        user.setProfileId(12131);
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void upDateUser(UserDTO user) {
        Users users = new Users();
        Optional<Users> optionalUsers = userDetailsServer.getUserPrincipalByUsername(user.getProfileId());
        optionalUsers.ifPresent(value -> users.setId(value.getId()));
        users.setProfileId(user.getProfileId());
        users.setRole(user.getRole());
        users.setPassword(user.getPassword());
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepository.save(users);

    }

    @Transactional(readOnly = true)
    @Override
    public Users getByIdUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void register(UserDTO user) {
        Users user1 = new Users();
        user1.setProfileId(user.getProfileId());
        user1.setRole(user.getRole());
        user1.setPassword(user.getPassword());

        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        userRepository.save(user1);

    }
}
