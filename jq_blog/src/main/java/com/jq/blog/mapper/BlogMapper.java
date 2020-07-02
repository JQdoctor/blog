package com.jq.blog.mapper;

import com.jq.blog.entities.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * Created by user on 2020/4/28.
 */
@Mapper
public  interface BlogMapper {
    public void insertBlog(Blog blog);

    public  Blog getBlogById(Integer id);

    public Collection<Blog> getAllBlogs(@Param("page") Integer page);

    public void updateBlogHits(@Param("hits") Integer hits, @Param("id")Integer id);

    public void updateBlogAudit(@Param("audit") Integer audit, @Param("id")Integer id);

    public void updateBlogComments(@Param("comments") Integer comments, @Param("id")Integer id);

    public int countBlogs();

    public Collection<Blog> getNewBlogs();

    public Collection<Blog> searchBlogs(@Param("title")String title,@Param("tags")String tags);

    public Collection<Blog> getAllBlogsBMS(@Param("page") Integer page);

    public void delectBlogById(@Param("id")Integer id);

    public Collection<Blog> searchBlogsBMS(@Param("title")String title,@Param("tags")String tags,@Param("audit")Integer audit);
}
