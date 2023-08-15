package com.posts.demo.service;

import com.posts.demo.dao.UserDao;
import com.posts.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    EntityManager entityManager;
    UserDao userDao;

    public UserServiceImp(EntityManager entityManager, UserDao userDao){
        this.entityManager = entityManager;
        this.userDao = userDao;
    }


    @Override
    public User findUserByUsername(String name) {
        Optional<User> result = userDao.findById(name);

        User user = null;
        if(result.isPresent()){
            user = result.get();
        }

        return user;
    }

    @Override
    public List<User> searchForUserByUsername(String name) {
        String username = name.toLowerCase();

        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username like :data",User.class);
        query.setParameter("data", "%" + username + "%");

        List<User> users = query.getResultList();

        return users;
    }



    @Override
    public User findUserAndPostsByUsername(String name) {

        TypedQuery<User> query = entityManager.createQuery("select u from User u join fetch u.posts where u.username = :data", User.class);
        query.setParameter("data", name);

        User user = query.getSingleResult();

        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }
}
