package com.posts.demo.service;

import com.posts.demo.entity.User;

import java.util.List;

public interface UserService {


    User findUserByUsername(String name);

    List<User> searchForUserByUsername(String name);


    User findUserAndPostsByUsername(String name);

    List<User> findAllUsers();

    void save(User user);

    void update(User user);
}
