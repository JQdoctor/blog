package com.jq.blog.service;

import com.jq.blog.entities.Mail;
import com.jq.blog.mapper.MailMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * Created by user on 2020/5/30.
 */
@Service
public class MailService {

    @Autowired
    MailMapper mailMapper;

    public void insertMail(Mail mail){
        mailMapper.insertMail(mail);
    }

    @CacheEvict(key = "#id")
    public void deleteMailByEmail(@Param("email") String email){
        mailMapper.deleteMailByEmail(email);
    }
}
