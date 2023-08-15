package com.posts.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private String username;


    @Column(name = "pw")
    private String password;

    @Column(name = "active")
    private int active;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "about")
    private String about;

    @Column(name = "image")
    private String image;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Post> posts;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Follower> followers;


    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Following> followings;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Role> roles;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Reply> replies;


    public User(String username, String password, int active, String nickName, String email) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.nickName = nickName;
        this.email = email;
    }

    public User(String username, String email, String about) {
        this.username = username;
        this.email = email;
        this.about = about;
    }

    public User(String email, String about) {
        this.email = email;
        this.about = about;
    }

    public User() {

    }

    public User(String username, String password, int active, String nickName, String email, String about, String image) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.nickName = nickName;
        this.email = email;
        this.about = about;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Follower> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
    }

    public List<Following> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Following> followings) {
        this.followings = followings;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", about='" + about + '\'' +
                '}';
    }

    public void addPost(Post post){

        if(posts == null){
            posts = new ArrayList<>();
        }

        posts.add(post);
    }

    public void deletePost(Post post){

        posts.remove(post);
    }


    public void addFollowing(Following following){

        if(followings == null){
            followings = new ArrayList<>();
        }

        followings.add(following);
    }

    public void addFollower(Follower follower){

        if(followers == null){
            followers = new ArrayList<>();
        }
        followers.add(follower);
    }
    public void addReply(Reply reply){

        if(replies == null){
            replies = new ArrayList<>();
        }
        replies.add(reply);
    }

    public boolean isFollowing(String username){

        for(int i = 0; i< followings.size(); i++){
            if(followings.get(i).getFollowing_id().equals(username)){
                return true;
            }
        }

        return false;
    }

    public void addRole(Role role){

        if(roles == null){
            roles = new ArrayList<>();
        }
        roles.add(role);
    }




}
