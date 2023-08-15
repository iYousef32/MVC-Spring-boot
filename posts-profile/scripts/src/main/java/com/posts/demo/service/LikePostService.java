package com.posts.demo.service;

import com.posts.demo.entity.LikePost;

public interface LikePostService {

    void save(LikePost likePost);

    void deleteById(int theId);
}
