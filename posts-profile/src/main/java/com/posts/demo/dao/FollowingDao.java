package com.posts.demo.dao;

import com.posts.demo.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingDao extends JpaRepository<Following, String> {
}
