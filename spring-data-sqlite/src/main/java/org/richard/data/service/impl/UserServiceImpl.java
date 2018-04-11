package org.richard.data.service.impl;

import org.richard.data.domain.User;
import org.richard.data.repository.UserRepository;
import org.richard.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by rnkoaa on 6/4/15.
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findOne(Long id) {
        User user = userRepository.findOne(id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Iterable<User> found = userRepository.findAll();
        if (found != null) {
            found.forEach(users::add);
        }
        return users;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User item) {
        return null;
    }

    @Override
    public boolean delete(User item) {
        return false;
    }
}
