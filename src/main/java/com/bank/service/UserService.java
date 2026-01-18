package com.bank.service;

import com.bank.exception.UserNotFoundException;
import com.bank.exception.UsernameAlreadyExistsException;
import com.bank.user.User;
import com.bank.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        // password BCrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // balance pre-set 0
        if (user.getBalance() == null) {
            user.setBalance(BigDecimal.ZERO);
        }

        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
