package com.jq.blog.mapper;

import com.jq.blog.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * Created by user on 2020/5/11.
 */
public interface UserMapper {

    public void insertUser(User user);

    public String selectPwByUn(@Param("username") String username);

    public void updateUser(User user);

    public User selectUserByUn(@Param("username")String username);

    public void delectUserByUn(@Param("username")String username);

    public Collection<User> getUsers();

    public void delectUserById(@Param("id")Integer id);

    public String selectLevelnameByLid(@Param("lid")Integer lid);

    public String changeUserLid(@Param("lid")Integer lid,@Param("id")Integer id);
}
