package com.posts.demo.dao;

import com.posts.demo.entity.LikeReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeReplyDao extends JpaRepository<LikeReply, Integer> {
}
