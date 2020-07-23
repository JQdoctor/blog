package com.jq.blog.controller;

import com.jq.blog.entities.Blog;
import com.jq.blog.entities.Comment;
import com.jq.blog.entities.Contact;
import com.jq.blog.entities.Mail;
import com.jq.blog.service.BlogService;
import com.jq.blog.service.CommentService;
import com.jq.blog.service.ContactService;
import com.jq.blog.service.MailService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Created by user on 2020/5/1.
 */
@Controller
public class BlogController {
    @Autowired
    BlogService blogMapper;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    CommentService commentMapper;

    @Autowired
    MailService mailMapper;

    @Autowired
    ContactService contactMapper;

    @RequestMapping({"","index","index.html"})
    public String index(Model model, HttpSession session){
        Collection<Blog> newBlogs = blogMapper.getNewBlogs();
        model.addAttribute("newBlogs",newBlogs);
        if(session.getAttribute("user")==null){
            session.setAttribute("user",null);
        }
        return "index";
    }

    @PostMapping("/blog")
    public String insertBlog(Blog blog) throws ParseException {
        Date date = new Date();//获得系统时间.
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String nowTime = sdf.format(date);
        Date time = sdf.parse( nowTime );
        blog.setTime(time);
        blog.setHits(0);
        blog.setComments(0);
        blog.setAudit(0);
        blogMapper.insertBlog(blog);
        return "loding";
    }

    @GetMapping("/blog/{id}")
    public String getBlogById(@PathVariable("id")Integer id, Model model,@Param("hits") Integer hits) throws ParseException {
        Blog blog0=blogMapper.getBlogById(id);
        hits=blog0.getHits()+1;
        int count=commentMapper.countCommentsByBid(id);
        blogMapper.updateBlogHits(hits,blog0.getId());
        blogMapper.updateBlogComments(count,blog0.getId());
        Blog blog=blogMapper.getBlogById(id);
        model.addAttribute("blog",blog);
        Collection<Comment> comments=commentMapper.getCommentsByBid(id);
        model.addAttribute("comments",comments);
        return "post";
    }

    @GetMapping("/blogs")
    public String getBlogs(Model model){
        Collection<Blog> blogs=blogMapper.getAllBlogs(0);
        int count=blogMapper.countBlogs();
        int pageCount= (int) Math.floor((count+4)/4);
        model.addAttribute("blogs",blogs);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("page",1);
        return "blog";
    }

    @GetMapping("/blogs/{page}")
    public String getBlogsByPage(Model model,Integer page){
        int count=blogMapper.countBlogs();
        if(count<=4){
            Collection<Blog> blogs=blogMapper.getAllBlogs(0);
            int pageCount= (int) Math.floor((count+4)/4);
            model.addAttribute("blogs",blogs);
            model.addAttribute("pageCount",pageCount);
            model.addAttribute("page",1);
            return "blog";
        }else{
            Collection<Blog> blogs=blogMapper.getAllBlogs(page);
            int pageCount= (int) Math.floor((count+4)/4);
            Collection<Blog> newBlogs = blogMapper.getNewBlogs();
            model.addAttribute("newBlogs",newBlogs);
            model.addAttribute("blogs",blogs);
            model.addAttribute("pageCount",pageCount);
            model.addAttribute("page",page);
            return "blog";
        }
    }

    @PostMapping("/search")
    public String search(Model model,String search) {
        Collection<Blog> blogs = blogMapper.searchBlogs('%'+search+'%','%'+search+'%');
        if (!blogs.isEmpty()){
            int count=blogMapper.countBlogs();
            int pageCount= (int) Math.floor((count+4)/4);
            model.addAttribute("pageCount",pageCount);
            model.addAttribute("page",1);
            System.out.println(blogs.isEmpty());
            model.addAttribute("blogs",blogs);
            model.addAttribute("empty",1);
            return "blog";
        }
        int count=blogMapper.countBlogs();
        int pageCount= (int) Math.floor((count+4)/4);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("page",1);
        System.out.println(blogs.isEmpty());
        model.addAttribute("blogs",blogs);
        model.addAttribute("empty",0);
        return "blog";
    }

    @GetMapping("/search/{tags}")
    public String searchByTags(Model model,@PathVariable("tags")String tags) {
        Collection<Blog> blogs = blogMapper.searchBlogs(null,'%'+tags+'%');
        if (!blogs.isEmpty()){
            int count=blogMapper.countBlogs();
            int pageCount= (int) Math.floor((count+4)/4);
            model.addAttribute("pageCount",pageCount);
            model.addAttribute("page",1);
            System.out.println(blogs.isEmpty());
            model.addAttribute("blogs",blogs);
            model.addAttribute("empty",1);
            return "blog";
        }
        int count=blogMapper.countBlogs();
        int pageCount= (int) Math.floor((count+4)/4);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("page",1);
        System.out.println(blogs.isEmpty());
        model.addAttribute("blogs",blogs);
        model.addAttribute("empty",0);
        return "blog";
    }

    @GetMapping("/mail")
    public String mailTest() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
        message.setSubject("通知-订阅成功");
        message.setText("<h>恭喜你成功订阅了Blog特别文章或活动！！！</h>" +
                "<br>" +
                "<a href='localhost:8080'><button>前往主页</button></a>" +
                "<a href='localhost:8080'><button>取消订阅</button></a>",true);
        message.setTo("pjjwwe123@163.com");
        message.setFrom("573887452@qq.com");

        mailSender.send(mimeMessage);
        return "redirect:/index.html";
    }


    @PostMapping("/mail")
    public String Mail(Mail mail) throws MessagingException {
        mailMapper.insertMail(mail);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
        message.setSubject("通知-订阅成功");
        message.setText("<h>恭喜你成功订阅了Blog特别文章或活动！！！</h>" +
                "<br>" +
                "<a href='http://120.26.172.56:8080/'><button>前往主页</button></a>" +
                "<a href='http://120.26.172.56:8080/unsubscribe.html'><button>取消订阅</button></a>",true);
        message.setTo(mail.getEmail());
        message.setFrom("573887452@qq.com");

        mailSender.send(mimeMessage);
        return "redirect:/index.html";
    }

    @PostMapping("/deletemail")
    public String delectMail(String email) throws MessagingException {
        mailMapper.deleteMailByEmail(email);
        return "redirect:/index.html";
    }

    @PostMapping("/contact")
    public String insertContact(Contact contact){
        System.out.println(contact.getName());
        contactMapper.insertContact(contact);
        return "redirect:/index.html";
    }

    @PostMapping("/resume")
    public String insertContact(String email) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
        message.setSubject("个人简历");
        message.setText("<h>感谢你百忙之中能看到我的简历</h>" +
                "<br>" +
                "<a href='http://120.26.172.56:8080/'><button>前往主页</button></a>" ,true);
        message.setTo(email);
        message.setFrom("573887452@qq.com");
        ClassPathResource classPathResource = new ClassPathResource("file/resume.docx");
        InputStream inputStream = classPathResource.getInputStream();
        File file=new File("resume.docx");
        inputStreamToFile(inputStream,file);


        message.addAttachment("个人简历.docx", file);

        mailSender.send(mimeMessage);
        return "redirect:/index.html";
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
