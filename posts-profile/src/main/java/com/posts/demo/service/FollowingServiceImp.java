package com.posts.demo.service;

import com.posts.demo.dao.FollowingDao;
import com.posts.demo.entity.Following;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowingServiceImp implements FollowingService{

    FollowingDao followingDao;
    EntityManager entityManager;

    public  FollowingServiceImp(FollowingDao followingDao, EntityManager entityManager){
        this.followingDao = followingDao;
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void save(Following following) {
        followingDao.save(following);
    }

    @Override
    @Transactional
    public void remove(Following following) {
        followingDao.delete(following);
    }
}
