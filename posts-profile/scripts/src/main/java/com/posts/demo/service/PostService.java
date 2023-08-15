package com.posts.demo.service;

import com.posts.demo.entity.Post;
import com.posts.demo.entity.User;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface PostService {

    void save(Post post);

    Post findPostById(int theId);

    Post findPostAndRepliesById(int theId);

    List<Post> sortPostByIdDESC(List<User> users);

    void delete(int theId);


}
