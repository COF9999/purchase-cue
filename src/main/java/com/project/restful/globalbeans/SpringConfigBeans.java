package com.project.restful.globalbeans;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfigBeans {

    public static String urlBaseMicroComments;

    @Bean
    RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Value("${config.base.url.micro.comments}")
    public void setUriKernel(String baseUrl){
        urlBaseMicroComments = baseUrl;
    }
}
