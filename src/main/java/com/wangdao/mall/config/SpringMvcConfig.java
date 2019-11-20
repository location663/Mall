package com.wangdao.mall.config;


import com.wangdao.mall.converter.String2DateConverter;
import com.wangdao.mall.converter.StringArray2StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;


@Configuration
//@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    ConfigurableConversionService configurableConversionService;

    @PostConstruct
    public void addConverter(){
        configurableConversionService.addConverter(new String2DateConverter());
        configurableConversionService.addConverter(new StringArray2StringConverter());
    }

//    @PostConstruct
//    public void addConverter2(){
//        configurableConversionService.addConverter(new StringArray2StringConverter());
//    }

    @Bean
    @Primary
    public ConfigurableConversionService ConversionService(){
        return configurableConversionService;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/wx/storage/admin/**").addResourceLocations("file:C:/projectStaticSources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new UserIntercepter()).addPathPatterns("/user/");
    }
}
