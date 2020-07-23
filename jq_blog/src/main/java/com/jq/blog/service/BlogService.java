package com.jq.blog.service;

import com.jq.blog.entities.Blog;
import com.jq.blog.mapper.BlogMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by user on 2020/4/28.
 */
@Service
public  class BlogService {
    @Autowired
    BlogMapper blogMapper;

    public void insertBlog(Blog blog){
        blogMapper.insertBlog(blog);
    }

    @Cacheable(cacheNames = "blog")
    public  Blog getBlogById(Integer id){
        Blog blog = blogMapper.getBlogById(id);
        return blog;
    }

    @Cacheable(cacheNames = "blogs")
    public Collection<Blog> getAllBlogs(@Param("page") Integer page){
        Collection<Blog> blogs = blogMapper.getAllBlogs(page);
        return blogs;
    }


    public void updateBlogHits(@Param("hits") Integer hits, @Param("id") Integer id){
        blogMapper.updateBlogHits(hits,id);
    }

    public void updateBlogAudit(@Param("audit") Integer audit, @Param("id") Integer id){
        blogMapper.updateBlogAudit(audit,id);
    }

    public void updateBlogComments(@Param("comments") Integer comments, @Param("id") Integer id){
        blogMapper.updateBlogComments(comments,id);
    }

    public int countBlogs(){
        int i = blogMapper.countBlogs();
        return i;
    }

    @Cacheable(cacheNames = "newBlogs")
    public Collection<Blog> getNewBlogs(){
        Collection<Blog> newBlogs = blogMapper.getNewBlogs();
        return newBlogs;
    }

    @Cacheable(cacheNames = "seBlogs")
    public Collection<Blog> searchBlogs(@Param("title") String title, @Param("tags") String tags){
        Collection<Blog> blogs = blogMapper.searchBlogs(title, tags);
        return blogs;
    }

    @Cacheable(cacheNames = "allBlogs")
    public Collection<Blog> getAllBlogsBMS(@Param("page") Integer page){
        Collection<Blog> allBlogs = blogMapper.getAllBlogs(page);
        return allBlogs;
    }

    @CacheEvict(key = "#id")
    public void delectBlogById(@Param("id") Integer id){
        blogMapper.delectBlogById(id);

    }

    @Cacheable(cacheNames = "seBlogs")
    public Collection<Blog> searchBlogsBMS(@Param("title") String title,
                                           @Param("tags") String tags, @Param("audit") Integer audit){
        Collection<Blog> blogs = blogMapper.searchBlogsBMS(title, tags, audit);
        return blogs;
    }
}
