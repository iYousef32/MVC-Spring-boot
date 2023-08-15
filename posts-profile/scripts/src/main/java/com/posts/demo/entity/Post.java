package com.posts.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;

    @Column(name = "user_id")
    private String username;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "text")
    private String text;

    @Column(name = "image")
    private String image;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<LikePost> likes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private List<Reply> replies;



    public Post() {
    }

    public Post(String text, String image) {
        this.text = text;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<LikePost> getLikes() {
        return likes;
    }

    public void setLikes(List<LikePost> likes) {
        this.likes = likes;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public void addReply(Reply reply){

        if(replies == null){
            replies = new ArrayList<>();
        }
        replies.add(reply);
    }

    public void addLike(LikePost likePost){

        if(likes == null){
            likes = new ArrayList<>();
        }

        likes.add(likePost);
    }

    public boolean isLiked(String username, int postId){

        for(int i = 0; i<likes.size(); i++){
            if(likes.get(i).getUsername().equals(username) && likes.get(i).getPost_id() == postId){
                return likes.get(i).isLiked();
            }
        }
        return false;
    }

    public int returnLikeId(String username, int postId){

        for(int i = 0; i<likes.size(); i++){
            if(likes.get(i).getUsername().equals(username) && likes.get(i).getPost_id() == postId){
                return likes.get(i).getLike_id();
            }
        }
        return -1;
    }

    public List<String> likedUsers(){
        List<String> users = new ArrayList<>();
        for(int i = 0; i<likes.size(); i++){
            users.add(likes.get(i).getUsername());
        }
        return users;
    }
}
