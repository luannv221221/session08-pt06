package com.ra.model.service.user;

import com.ra.model.entity.User;

public interface UserService {
    boolean register(User user);
    User login(User user);
}
