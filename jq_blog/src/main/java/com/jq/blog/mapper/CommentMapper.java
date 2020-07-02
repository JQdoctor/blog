package com.jq.blog.mapper;

import com.jq.blog.entities.Comment;

import java.util.Collection;

/**
 * Created by user on 2020/5/7.
 */
public interface CommentMapper {
    public void insertComment(Comment comment);

    public Collection<Comment> getCommentsByBid(Integer bid);

    public int countCommentsByBid(Integer bid);
}
