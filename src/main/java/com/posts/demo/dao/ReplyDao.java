package com.posts.demo.dao;

import com.posts.demo.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyDao extends JpaRepository<Reply, Integer> {
}
