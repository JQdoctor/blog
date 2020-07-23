package com.jq.blog.service;

import com.jq.blog.entities.Comment;
import com.jq.blog.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by user on 2020/5/7.
 */
@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public void insertComment(Comment comment){
        commentMapper.insertComment(comment);
    }


    public Collection<Comment> getCommentsByBid(Integer bid){
        Collection<Comment> comments = commentMapper.getCommentsByBid(bid);
        return comments;
    }


    public int countCommentsByBid(Integer bid){
        int i = commentMapper.countCommentsByBid(bid);
        return i;
    }
}
