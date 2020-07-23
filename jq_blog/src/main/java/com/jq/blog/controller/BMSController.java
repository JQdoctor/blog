package com.jq.blog.controller;

import com.jq.blog.entities.Blog;
import com.jq.blog.entities.Comment;
import com.jq.blog.entities.User;
import com.jq.blog.service.BlogService;
import com.jq.blog.service.CommentService;
import com.jq.blog.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Collection;

/**
 * Created by user on 2020/5/11.
 */
@Controller
public class BMSController {
    @Autowired
    UserService userMapper;
    @Autowired
    BlogService blogMapper;
    @Autowired
    CommentService commentMapper;

    @GetMapping({"/login.html","/login"})
    public String returnLogin(HttpServletRequest request){
        Object user=request.getSession().getAttribute("user");
        if (user!=null){
            return "redirect:/main.html";
        }
        return "bms/login";
    }


    @GetMapping("/register")
    public String register(User user,String rusername,String rpassword,String email){
        System.out.println(rusername+','+rpassword+','+email);
        user.setUsername(rusername);
        user.setPassword(rpassword);
        user.setEamil(email);
        user.setLid(3);
        userMapper.insertUser(user);
        return "bms/loding";
    }

    @PostMapping("/login")
    public String login(String username,String password, Model model,HttpSession session){
        session.removeAttribute("user");
        System.out.println(password);
        String newPassword=userMapper.selectPwByUn(username);
        System.out.println(newPassword);
        if(password.equals(newPassword)){
            User user=userMapper.selectUserByUn(username);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(30*60);
            return "redirect:/main.html";
        }
        session.setAttribute("error","用户名不存在或密码错误！");
        return "redirect:/error.html";
    }

    @GetMapping("/logout")
    public String logout(Integer id,HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login.html";
    }

    @GetMapping("/user/id")
    public  String getUserByUsername(String username){
        User user=userMapper.selectUserByUn(username);
        return "";
    }

    @PostMapping("/user/update")
    public String updateUser(User user){
        userMapper.updateUser(user);
        return "";
    }

    @GetMapping("/user/delete{username}")
    public String delectUserByUsername(String username){
        userMapper.delectUserByUn(username);
        return "";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        Collection<User> users = userMapper.getUsers();
        model.addAttribute("users",users);
        return "";
    }

    @GetMapping("/users/delect{id}")
    public String delectUserById(Model model,Integer id){
        userMapper.delectUserById(id);
        return "";
    }

    @PostMapping("/users/change")
    public String changeUserLid(Model model,Integer id,Integer lid){
        userMapper.updateUserLid(id,lid);
        return "";
    }

    @GetMapping("/bloglist")
    public String selectBlogList(Model model){
        Collection<Blog> blogs = blogMapper.getAllBlogsBMS(0);
        model.addAttribute("empty",1);
        model.addAttribute("blogs",blogs);
        return "bms/blog/bloglist";
    }

    @GetMapping("/bloglist/{id}")
    public String getBlogById(@PathVariable("id")Integer id, Model model, @Param("hits") Integer hits) throws ParseException {
        Blog blog0=blogMapper.getBlogById(id);
        hits=blog0.getHits();
        int count=commentMapper.countCommentsByBid(id);
        blogMapper.updateBlogHits(hits,blog0.getId());
        blogMapper.updateBlogComments(count,blog0.getId());
        Blog blog=blogMapper.getBlogById(id);
        model.addAttribute("blog",blog);
        Collection<Comment> comments=commentMapper.getCommentsByBid(id);
        model.addAttribute("comments",comments);
        return "bms/blog/post";
    }

    @GetMapping("/bloglist/update/{id}")
    public String updateAudit(@PathVariable("id")Integer id,@Param("audit") Integer audit,Model model){
        blogMapper.updateBlogAudit(id,1);
        Collection<Blog> blogs = blogMapper.getAllBlogsBMS(0);
        model.addAttribute("blogs",blogs);
        return "bms/blog/bloglist";
    }

    @GetMapping("/bloglist/delect/{id}")
    public String delectBlogById(Model model,Integer id){
        blogMapper.delectBlogById(id);
        Collection<Blog> blogs = blogMapper.getAllBlogsBMS(0);
        model.addAttribute("blogs",blogs);
        return "bms/blog/bloglist";
    }

    @PostMapping("/bloglist/search")
    public String searchBlog(Model model,String title,String tags,Integer audit){
        System.out.println(audit);
        Collection<Blog> blogs = blogMapper.searchBlogsBMS('%'+title+'%','%'+tags+'%',audit);
        if (!blogs.isEmpty()){
        model.addAttribute("blogs",blogs);
        model.addAttribute("empty",1);
        return "bms/blog/bloglist";
        }
        model.addAttribute("empty",0);
        model.addAttribute("blogs",blogs);
        return "bms/blog/bloglist";
    }
}
