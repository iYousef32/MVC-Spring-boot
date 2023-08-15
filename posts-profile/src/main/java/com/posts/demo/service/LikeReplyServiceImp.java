package com.posts.demo.service;

import com.posts.demo.dao.LikeReplyDao;
import com.posts.demo.entity.LikeReply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeReplyServiceImp implements LikeReplyService{

    LikeReplyDao likeReplyDao;
    EntityManager entityManager;

    public LikeReplyServiceImp(LikeReplyDao likeReplyDao, EntityManager entityManager) {
        this.likeReplyDao = likeReplyDao;
        this.entityManager = entityManager;
    }

    @Override
    public LikeReply findLikeReply(String username, int replyId) {

        TypedQuery<LikeReply> query = entityManager.createQuery("select l from LikeReply l where l.username = :data and l.reply_id = :data2", LikeReply.class);
        query.setParameter("data", username);
        query.setParameter("data2", replyId);

        LikeReply likeReply = query.getSingleResult();

        return likeReply;
    }

    @Override
    @Transactional
    public void save(LikeReply likeReply) {
        likeReplyDao.save(likeReply);
    }

    @Override
    @Transactional
    public void delete(LikeReply likeReply) {
        likeReplyDao.delete(likeReply);
    }


}















