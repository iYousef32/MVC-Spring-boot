package com.posts.demo.service;

import com.posts.demo.dao.LikePostDao;
import com.posts.demo.entity.LikePost;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikePostServiceImp implements LikePostService{

    LikePostDao likePostDao;
    EntityManager entityManager;

    @Autowired
    public LikePostServiceImp(LikePostDao likePostDao, EntityManager entityManager){
        this.likePostDao = likePostDao;
        this.entityManager = entityManager;
    }


    @Override
    public void save(LikePost likePost) {
        likePostDao.save(likePost);
    }

    @Override
    public void deleteById(int theId) {
        likePostDao.deleteById(theId);
    }
}
