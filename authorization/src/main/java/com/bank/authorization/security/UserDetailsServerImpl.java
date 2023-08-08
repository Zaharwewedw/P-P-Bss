package com.bank.authorization.security;

import com.bank.authorization.model.Users;
import com.bank.authorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServerImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Users> getUserPrincipalByUsername(int username) {
        return  userRepository.getAllByProfileId(username);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.getAllByProfileId(Integer.parseInt(username));

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserSecurityDetailsRole(user.get());
    }
}
