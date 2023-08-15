package com.posts.demo.dao;

import com.posts.demo.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostDao extends JpaRepository<LikePost, Integer> {
}
