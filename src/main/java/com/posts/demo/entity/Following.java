package com.posts.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "following")
@IdClass(FollowingID.class)
public class Following {

    @Id
    @Column(name = "following_id")
    private String following_id;

    @Id
    @Column(name = "user_id")
    private String username;

    public Following() {
    }

    public Following(String following_id, String username) {
        this.following_id = following_id;
        this.username = username;
    }

    public Following(String following_id) {
        this.following_id = following_id;
    }

    public String getFollowing_id() {
        return following_id;
    }

    public void setFollowing_id(String following_id) {
        this.following_id = following_id;
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
                "following_id='" + following_id + '\'' +
                '}';
    }
}
