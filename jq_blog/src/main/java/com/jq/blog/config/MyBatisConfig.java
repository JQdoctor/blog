package com.jq.blog.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * Created by user on 2020/5/1.
 */
@Configuration
public class MyBatisConfig {
    public ConfigurationCustomizer configurationCustomizer(){
        return  new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
