package com.posts.demo.service;

import com.posts.demo.entity.LikeReply;

public interface LikeReplyService {

    LikeReply findLikeReply(String username, int replyId);
    void save(LikeReply likeReply);

    void delete(LikeReply likeReply);
}
