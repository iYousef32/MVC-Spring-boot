package com.posts.demo.entity;

import com.posts.demo.service.UserService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "replies")
public class Reply {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private int idReply;

    @Column(name = "user_id")
    private String username;

    @Column(name = "user_image")
    private String avatar;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "text")
    private String textReply;

    @Column(name = "image")
    private String imageReply;

    @OneToMany
    @JoinColumn(name = "reply_id")
    private List<LikeReply> likes;

    public Reply() {
    }

    public Reply(String textReply, String imageReply) {
        this.textReply = textReply;
        this.imageReply = imageReply;
    }

    public int getIdReply() {
        return idReply;
    }

    public void setIdReply(int id) {
        this.idReply = id;
    }

    public String getTextReply() {
        return textReply;
    }

    public void setTextReply(String text) {
        this.textReply = text;
    }

    public String getImageReply() {
        return imageReply;
    }

    public void setImageReply(String image) {
        this.imageReply = image;
    }

    public List<LikeReply> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeReply> like) {
        this.likes = like;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + idReply +
                ", text='" + textReply + '\'' +
                ", image='" + imageReply + '\'' +
                '}';
    }

    public boolean isLiked(String username, int replyId){

        for(int i = 0; i<likes.size(); i++){
            if(likes.get(i).isLiked(username,replyId)){
                return true;
            }
        }

        return false;
    }

    public void addLike(LikeReply likeReply){

        if(likes == null){
            likes = new ArrayList<>();
        }
        likes.add(likeReply);
    }

    public List<String> likedUsers(){
        List<String> users = new ArrayList<>();
        for(int i = 0; i<likes.size(); i++){
            users.add(likes.get(i).getUsername());
        }
        return users;
    }


}
