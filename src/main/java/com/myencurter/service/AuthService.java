package com.myencurter.service;

import com.myencurter.model.User;
import com.myencurter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUser(String email) {

        return userRepository.findByEmail(email);
    }
}
