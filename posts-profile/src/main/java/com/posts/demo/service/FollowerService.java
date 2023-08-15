package com.posts.demo.service;

import com.posts.demo.entity.Follower;
import com.posts.demo.entity.Following;

public interface FollowerService {

    void save(Follower follower);

    void remove(Follower follower);
}
