package com.jq.blog.service;

import com.jq.blog.entities.User;
import com.jq.blog.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by user on 2020/5/11.
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    public String selectPwByUn(@Param("username") String username){
        String s = userMapper.selectPwByUn(username);
        return s;
    }

    public void updateUser(User user){
        userMapper.updateUser(user);
    }

    @Cacheable(cacheNames = "user")
    public User selectUserByUn(@Param("username") String username){
        User user = userMapper.selectUserByUn(username);
        return user;
    }

    @CacheEvict(key = "#id")
    public void delectUserByUn(@Param("username") String username){
        userMapper.delectUserByUn(username);
    }

    @Cacheable(cacheNames = "users")
    public Collection<User> getUsers(){
        Collection<User> users = userMapper.getUsers();
        return users;
    }

    @CacheEvict(key = "#id")
    public void delectUserById(@Param("id") Integer id){
        userMapper.delectUserById(id);
    }

    public String selectLevelnameByLid(@Param("lid") Integer lid){
        String s = userMapper.selectLevelnameByLid(lid);
        return s;
    }

    public String updateUserLid(@Param("lid") Integer lid, @Param("id") Integer id){
        String s = userMapper.updateUserLid(lid, id);
        return s;
    }
}
