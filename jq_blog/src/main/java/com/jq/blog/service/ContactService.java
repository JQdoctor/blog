package com.jq.blog.service;

import com.jq.blog.entities.Contact;
import com.jq.blog.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * Created by user on 2020/6/1.
 */
@Service
public class ContactService {

    @Autowired
    ContactMapper contactMapper;

    @CachePut(value = "contact",key = "#result.id")
    public void insertContact(Contact contact){
        contactMapper.insertContact(contact);
    }
}
