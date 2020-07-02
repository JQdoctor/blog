package com.jq.blog.mapper;

import com.jq.blog.entities.Mail;
import org.apache.ibatis.annotations.Param;

/**
 * Created by user on 2020/5/30.
 */
public interface MailMapper {

    public void insertMail(Mail mail);

    public void deleteMailByEmail(@Param("email")String email);
}
