package com.posts.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "like_post")
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private int like_id;

    @Column(name = "liked")
    private boolean isLiked;

    @Column(name = "user_id")
    private String username;

    @Column(name = "post_id")
    private int post_id;

    public LikePost() {
    }

    public LikePost(String username, int post_id, boolean isLiked) {
        this.isLiked = isLiked;
        this.username = username;
        this.post_id = post_id;
    }

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    @Override
    public String toString() {
        return "LikePost{" +
                "like_id=" + like_id +
                ", isLiked=" + isLiked +
                ", username='" + username + '\'' +
                ", post_id='" + post_id + '\'' +
                '}';
    }


}
