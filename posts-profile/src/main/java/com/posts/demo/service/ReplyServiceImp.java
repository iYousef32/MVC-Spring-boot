package com.posts.demo.service;

import com.posts.demo.dao.ReplyDao;
import com.posts.demo.entity.Reply;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReplyServiceImp implements ReplyService {

    ReplyDao replyDao;
    EntityManager entityManager;


    public ReplyServiceImp(ReplyDao replyDao, EntityManager entityManager){
        this.replyDao = replyDao;
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void save(Reply reply) {
        replyDao.save(reply);
    }

    @Override
    public Reply findReplyById(int theId) {
        Optional<Reply> result = replyDao.findById(theId);

        Reply reply = null;
        if(result.isPresent()){
            reply = result.get();
        }

        return reply;
    }

    @Override
    @jakarta.transaction.Transactional
    public void delete(int theId) {
        replyDao.deleteById(theId);
    }
}
