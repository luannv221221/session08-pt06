package com.ra.model.dao.user;

import com.ra.model.entity.User;

public interface UserDAO {
    boolean create(User user);
    User findUserByEmail(String email);
}
