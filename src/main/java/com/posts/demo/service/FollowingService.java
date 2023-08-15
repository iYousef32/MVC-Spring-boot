package com.posts.demo.service;

import com.posts.demo.entity.Following;

public interface FollowingService {

    void save(Following following);

    void remove(Following following);
}
