package com.ruslan.pricelist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.nio.charset.Charset;

/**
 * Created by xvitcoder on 12/24/15.
 */
@Configuration
public class AppConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520L);
        multipartResolver.setMaxInMemorySize(1048576);
        return multipartResolver;
    }

    @Bean
    public StringHttpMessageConverter getStringHttpMessageConverter(){
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(Charset.forName("UTF8"));
        return messageConverter;
    }
}
