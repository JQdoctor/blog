package com.jq.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.jq.blog.mapper")
@SpringBootApplication
public class JqBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(JqBlogApplication.class, args);
	}

}
