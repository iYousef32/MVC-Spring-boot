package com.posts.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "follower")
@IdClass(FollowerID.class)
public class Follower {
    @Id
    @Column(name = "follower_id")
    private String follower_id;

    @Id
    @Column(name = "user_id")
    private String username;

    public Follower() {
    }

    public Follower(String follower_id, String username) {
        this.follower_id = follower_id;
        this.username = username;
    }

    public Follower(String following_id) {
        this.follower_id = following_id;
    }

    public String getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(String following_id) {
        this.follower_id = following_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Following{" +
                "following_id='" + follower_id + '\'' +
                '}';
    }
}
