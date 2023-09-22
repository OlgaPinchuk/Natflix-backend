package com.novare.natflix.dao.user;

import com.novare.natflix.models.user.User;

import java.util.List;

public interface IUserDao {
    List<User> getAll();
    User get(long id);
    User findByEmail(String email);
    User register(User user);
}
