package com.souret.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class AddCustomLocations implements WebMvcConfigurer {

//    @Bean
//    WebMvcConfigurer configurer () {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addResourceHandlers (ResourceHandlerRegistry registry) {
//                registry.addResourceHandler("/img/**")
//                        .addResourceLocations("file:/Users/chadirahme/IdeaProjects/souret-ws/ProfilePicture/");
//
////                registry.addResourceHandler("/pages/**").
////                        addResourceLocations("classpath:/my-custom-location/");
//            }
//        };
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/img/**")
                .addResourceLocations("file:/Users/chadirahme/IdeaProjects/souret-ws/ProfilePicture/");
    }
}
