package com.jq.blog.controller;

import com.jq.blog.entities.Comment;
import com.jq.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2020/5/7.
 */
@Controller
public class CommentController {
    @Autowired
    CommentService commentMapper;

    @PostMapping("/comment")
    public String insertComment(Comment comment) throws ParseException {
        Date date = new Date();//获得系统时间.
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String nowTime = sdf.format(date);
        Date time = sdf.parse( nowTime );
        comment.setTime(time);
        commentMapper.insertComment(comment);
        return "redirect:/blog/"+comment.getBid();
    }


}
