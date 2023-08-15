package com.posts.demo.dao;

import com.posts.demo.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerDao extends JpaRepository<Follower, String> {
}
