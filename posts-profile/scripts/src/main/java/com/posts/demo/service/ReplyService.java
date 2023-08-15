package com.posts.demo.service;


import com.posts.demo.entity.Reply;

public interface ReplyService {

    void save(Reply reply);

    Reply findReplyById(int theId);

    void delete(int theId);
}
