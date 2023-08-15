package com.posts.demo.service;

import com.posts.demo.dao.FollowerDao;
import com.posts.demo.dao.FollowingDao;
import com.posts.demo.entity.Follower;
import com.posts.demo.entity.Following;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowerServiceImp implements FollowerService {

    FollowerDao followerDao;
    EntityManager entityManager;

    public FollowerServiceImp(FollowerDao followerDao, EntityManager entityManager){
        this.followerDao = followerDao;
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void save(Follower follower) {
        followerDao.save(follower);
    }

    @Override
    @Transactional
    public void remove(Follower follower) {
        followerDao.delete(follower);
    }
}
