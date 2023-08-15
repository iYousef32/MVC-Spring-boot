package com.posts.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "like_reply")
@IdClass(LikeReplyID.class)
public class LikeReply {

    @Id
    @Column(name = "user_id")
    private String username;

    @Id
    @Column(name = "reply_id")
    private int reply_id;

    @Column(name = "liked")
    private boolean isLiked;

    public LikeReply(String username, int reply_id) {
        this.username = username;
        this.reply_id = reply_id;
    }

    public LikeReply(String username, int reply_id, boolean isLiked) {
        this.username = username;
        this.reply_id = reply_id;
        this.isLiked = isLiked;
    }

    public LikeReply() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
    public boolean isLiked(String username, int replyId){

            if(username.equals(this.username) && replyId == this.reply_id){
                return isLiked;
            }

        return false;
    }
}
