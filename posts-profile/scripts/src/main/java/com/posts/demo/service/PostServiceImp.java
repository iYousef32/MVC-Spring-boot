package com.posts.demo.service;

import com.posts.demo.dao.PostDao;
import com.posts.demo.dao.UserDao;
import com.posts.demo.entity.Post;
import com.posts.demo.entity.Reply;
import com.posts.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService{

    EntityManager entityManager;
    PostDao postDao;

    public PostServiceImp(EntityManager entityManager, PostDao postDao){
        this.entityManager = entityManager;
        this.postDao = postDao;
    }

    @Override
    @Transactional
    public void save(Post post) {
            postDao.save(post);

    }

    @Override
    public Post findPostById(int theId) {
        Optional<Post> result = postDao.findById(theId);

        Post post = null;
        if(result.isPresent()){
            post = result.get();
        }

        return post;
    }

    @Override
    public Post findPostAndRepliesById(int theId) {

        TypedQuery<Post> query = entityManager.createQuery("select p from Post p join fetch p.replies where p.id = :data", Post.class);
        query.setParameter("data", theId);

        Post post = query.getSingleResult();

        return post;
    }

    @Override
    public List<Post> sortPostByIdDESC(List<User> users) {
        List<Post> posts = new ArrayList<>();
        List<Integer> postsId = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            posts.addAll(users.get(i).getPosts());
        }
        for(int i = 0; i < posts.size(); i++){
            postsId.add(posts.get(i).getId());
        }


        TypedQuery<Post> query = entityManager.createQuery("select p from Post p where p.id in :data ORDER BY p.id DESC", Post.class);
        query.setParameter("data", postsId);

        List<Post> postList = query.getResultList();

        return postList;
    }

    @Override
    public void delete(int theId) {
        postDao.deleteById(theId);
    }
}
