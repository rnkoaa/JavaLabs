package com.richard.service.impl;

import com.richard.domain.User;
import com.richard.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by rnkoaa on 3/25/15.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public User find(String id) {
        return new User();
    }
}
